package models
import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._
import javax.jdo.JDOHelper
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

import models.auth.User

case class SchoolInfo(name: String, score: Double, coaches: String)

@PersistenceCapable(detachable="true")
@Unique(name="DISTRICT_WITH_YEAR", members=Array("_glmlId", "_year"))
class District {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Column(allowsNull="false", length=1)
  private[this] var _glmlId: String = _ 
  private[this] var _year: Year = _
  
  def this(glmlId: String, year: Year) = {
    this()
    glmlId_=(glmlId)
    year_=(year)
  }
  
  def id: Long = _id
  
  def glmlId: String = _glmlId
  def glmlId_=(theGlmlId: String) { _glmlId = theGlmlId }
  
  def year: Year = _year
  def year_=(theYear: Year) { _year = theYear }
  
  def getTopSchools(testDate: Option[TestDate]): List[(String, BigDecimal, Set[String])] = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QSchoolId.candidate
    if (!testDate.isEmpty) {
      val cand2 = QTest.candidate
      val testListDate = pm.query[Test].filter(cand2.testDate.eq(testDate.get)).executeList()
      val schoolIdList = pm.query[SchoolId].filter(cand.district.eq(this)).executeList()
      schoolIdList.map((s: SchoolId) => 
          (s.school.name, 
           testListDate.filter((t: Test) => t.studentId.schoolId.eq(s)).foldLeft(BigDecimal(0.0))((sum, test) => test.score + sum),
           s.coachesNames
          )
      ).sortBy(_._2).reverse
    } else {
      pm.query[SchoolId].filter(cand.district.eq(this)).executeList().map((s: SchoolId) => (s.school.name, s.getCumulativeScore, s.coachesNames)).sortBy(_._2).reverse
    }
  }
  
  def getTopStudents(testDate: Option[TestDate]): List[(Int, List[(Int, StudentId, BigDecimal)])] = {
    def makePlaceList(list: List[(StudentId, BigDecimal)]): List[(Int, StudentId, BigDecimal)] = {
      val nondistinct = list.unzip._2.sorted.reverse
      val distinct = list.unzip._2.distinct.sorted.reverse.take(10)
      val scoresAndNames = for(score <- distinct) yield (score, list.filter(_._2 == score).map(_._1))
      val places = scoresAndNames.flatMap((t: (BigDecimal, List[StudentId])) => t._2.map((nondistinct.indexOf(t._1) + 1, _, t._1)))
      places.takeWhile(_._1 <= 10)
    }
    
    val pm: ScalaPersistenceManager = DataStore.pm
    if (!testDate.isEmpty) {
      val cand = QTest.candidate
      val testListDateAndDistrict = pm.query[Test].filter(cand.testDate.eq(testDate.get)).executeList().filter(
          (t: Test) => t.studentId.schoolId.district.eq(this))
      val grades = List(9, 10, 11, 12)
      grades.map((grade: Int) => 
          (grade,
           makePlaceList(testListDateAndDistrict.filter((t: Test) => t.studentId.grade == grade).map((t: Test) => (t.studentId, t.score)))
          )
      )
    } else {
      val cand = QStudentId.candidate
      val varble = QSchoolId.variable("SchoolId")
      val testDistrictList = pm.query[StudentId].filter(cand.schoolId.eq(varble).and(varble.district.eq(this))).executeList()
      val grades = List(9, 10, 11, 12)
      grades.map((grade: Int) => 
          (grade,
           makePlaceList(testDistrictList.filter((s: StudentId) => s.grade == grade).map((s: StudentId) => (s, s.getCumulativeScore)))
          )
      )
    }
  }
  
  override def toString: String = "District(id=%d)".format(id)
}

object District {
/*
    @staticmethod
    def get_or_create_answer_district(year=None):
        if not year:
            year = Year.get_current_year()
        return District.objects.get_or_create(glml_id=ANSWER_STUDENT_ID[0],
                                              year=year)
*/
  
  def getOrCreateAnswerKeyDistrict(maybeYear: Option[Year] = None): District = {
    val glmlId = StudentId.answerKeyStudentId.substring(0, 1)
    val year = maybeYear.getOrElse(Year.currentYear)
    val cand = QDistrict.candidate
    DataStore.pm.query[District].filter(cand.glmlId.eq(glmlId).and(
        cand.year.eq(year))).executeOption() match {
      case Some(d) => d
      case None => {
        val newDistrict = new District(glmlId, year)
        DataStore.pm.makePersistent(newDistrict)
        newDistrict
      }
    }
  }
}

trait QDistrict extends PersistableExpression[District] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _glmlId: StringExpression = new StringExpressionImpl(this, "_glmlId")
  def glmlId: StringExpression = _glmlId
  
  private[this] lazy val _year: ObjectExpression[Year] = new ObjectExpressionImpl[Year](this, "_year")
  def year: ObjectExpression[Year] = _year
}

object QDistrict {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QDistrict = {
    new PersistableExpressionImpl[District](parent, name) with QDistrict
  }
  
  def apply(cls: Class[District], name: String, exprType: ExpressionType): QDistrict = {
    new PersistableExpressionImpl[District](cls, name, exprType) with QDistrict
  }
  
  private[this] lazy val jdoCandidate: QDistrict = candidate("this")
  
  def candidate(name: String): QDistrict = QDistrict(null, name, 5)
  
  def candidate(): QDistrict = jdoCandidate
  
  def parameter(name: String): QDistrict = QDistrict(classOf[District], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QDistrict = QDistrict(classOf[District], name, ExpressionType.VARIABLE)
}



