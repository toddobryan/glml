package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

import org.joda.time.{DateTime, LocalDate}
import java.io.File
import org.apache.poi.ss.usermodel._
import collection.JavaConverters._

@PersistenceCapable(detachable="true")
class TestDate {
  private[this] var _id: Int = _
  @Persistent(defaultFetchGroup="true")
  @Unique
  private[this] var _date: java.sql.Date = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _year: Year = _
  
  def this(date: LocalDate, year: Year) {
    this()
    date_=(date)
    year_=(year)
  }
  
  def id: Int = _id
  
  def date: LocalDate = new DateTime(_date).toLocalDate
  def date_=(theDate: LocalDate) { 
    _date = new java.sql.Date(theDate.toDateTimeAtStartOfDay.toDate.getTime)
  }
  
  def year: Year = _year
  def year_=(theYear: Year) { _year = theYear }
  
  def getKey(): Test = {
    val answerKeyStudentId = "999999"
    val answerKeyStudentName = "KEY"
    
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QTest.candidate
    val testsThisDate = pm.query[Test].filter(cand.testDate.eq(this)).executeList()
    testsThisDate.filter(_.studentId.glmlId == answerKeyStudentId)(0)
  } 
  
  def pdf {} //TODO
  
  /*
   def pdf(self):
        import os
        from django.conf import settings
        filename = os.path.join(u'archive', self.date.strftime('%Y-%m-%d') + u'.pdf')
        if os.path.exists(os.path.join(settings.MEDIA_ROOT, filename)):
            return settings.MEDIA_URL + filename
        else:
            return None
   */
  
  override def toString: String = "%s: %s".format(year.slug, date)
}

object TestDate {
  def importTest(date: LocalDate, file: File) {
    val pm = DataStore.pm
    val cand = QYear.candidate
    
    val year = pm.query[Year].filter(cand.start.eq(date.getYear)).executeOption() getOrElse Year.currentYear
    val testDate = new TestDate(date, year)
    pm.makePersistent(testDate)
    
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)
    val rows = sheet.rowIterator().asScala.toList.filter( row =>
        row.getCell(1).getStringCellValue().matches("""\d{6}""") ||
        row.getCell(0).getStringCellValue() == "Key"
        )
        
    val stuCand = QStudentId.candidate
    val schoolVarble = QSchoolId.variable("SchoolId")
    val districtVarble = QDistrict.variable("District")
    rows foreach { row =>
      if (row.getCell(0).getStringCellValue() == "Key") row.getCell(1).setCellValue("999999")
      
      val studentId = pm.query[StudentId].filter(
          stuCand.glmlId.eq(row.getCell(1).getStringCellValue()) and 
          stuCand.schoolId.eq(schoolVarble).and(schoolVarble.district.eq(districtVarble).and(districtVarble.year.eq(testDate.year)))
          ).executeOption()
      val test = new Test(testDate, studentId.getOrElse(null), BigDecimal(0.0))
      pm.makePersistent(test)
      
      row.cellIterator().asScala.toList foreach { cell =>
        val index = cell.getColumnIndex()
        if (index > 4 && index < 29) {
          val question = new Question(index - 4, cell.getStringCellValue(), test)
          pm.makePersistent(question)
        }
      }
      test.rescore()
    }
  }
}

trait QTestDate extends PersistableExpression[TestDate] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _date: DateExpression[java.util.Date] = new DateExpressionImpl[java.util.Date](this, "_date")
  def date: DateExpression[java.util.Date] = _date
  
  private[this] lazy val _year: ObjectExpression[Year] = new ObjectExpressionImpl[Year](this, "_year")
  def year: ObjectExpression[Year] = _year
}

object QTestDate {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QTestDate = {
    new PersistableExpressionImpl[TestDate](parent, name) with QTestDate
  }
  
  def apply(cls: Class[TestDate], name: String, exprType: ExpressionType): QTestDate = {
    new PersistableExpressionImpl[TestDate](cls, name, exprType) with QTestDate
  }
  
  private[this] lazy val jdoCandidate: QTestDate = candidate("this")
  
  def candidate(name: String): QTestDate = QTestDate(null, name, 5)
  
  def candidate(): QTestDate = jdoCandidate
  
  def parameter(name: String): QTestDate = QTestDate(classOf[TestDate], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QTestDate = QTestDate(classOf[TestDate], name, ExpressionType.VARIABLE)
}
