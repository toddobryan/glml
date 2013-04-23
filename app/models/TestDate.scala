package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

import org.joda.time.{DateTime, LocalDate}
import java.io.File

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
  def importTest(date: LocalDate, excel: File) {
    //TODO
  }
  
  /*
    @staticmethod
    def import_test(cleaned_data):
        test_date = TestDate.objects.create(date=cleaned_data['date'])
        sheet = cleaned_data['excel_file']
        i = 0
        rows = []
        import re
        while i < sheet.nrows:
            row = [cell.value for cell in sheet.row(i)][:25]
            if row and re.match(r'\d{6}', unicode(row[0])[:6]):
                rows.append(row)
            i += 1
        for row in rows:
            student_id = StudentID.objects.get(glml_id=unicode(row[0])[:6], school_id__district__year__id=test_date.year.id)
            test = Test.objects.create(test_date=test_date, student_id=student_id, score=0)
            i = 1
            while i < len(row):
                q = Question.objects.create(number=i, test=test, answer=row[i])
                i += 1
        for test in test_date.test_set.all():
            test.rescore()
   */
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
