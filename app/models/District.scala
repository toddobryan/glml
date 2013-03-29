package models
import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._
import javax.jdo.JDOHelper
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore
import java.math.BigDecimal

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
           testListDate.filter((t: Test) => t.studentId.schoolId.eq(s)).foldRight[BigDecimal](new BigDecimal(0.0))(
             (t: Test, sum: BigDecimal) => new BigDecimal(t.score).add(sum)), 
           s.coachesNames
          )
      ).sortBy(_._2).reverse
    } else {
      pm.query[SchoolId].filter(cand.district.eq(this)).executeList().map((s: SchoolId) => (s.school.name, s.getCumulativeScore, s.coachesNames)).sortBy(_._2).reverse
    }
  }
  
  def getTopStudents(testDate: Option[TestDate]): List[(Int, List[(Int, StudentId, BigDecimal)])] = {
    def makePlaceList(list: List[(StudentId, BigDecimal)]): List[(Int, StudentId, BigDecimal)] = {
      list match {
        case Nil => Nil
        case x :: xs => {
          val listSorted = list.sortBy(_._2).reverse
          val first = (1, listSorted.head._1, listSorted.head._2)
          def placeListHelp(list: List[(StudentId, BigDecimal)], buildList: List[(Int, StudentId, BigDecimal)], last: (Int, StudentId, BigDecimal), place: Int): List[(Int, StudentId, BigDecimal)] = {
            list match {
              case Nil => buildList
              case x :: xs => {
                if(x._2 == last._3) {
                  val next = (last._1, x._1, x._2)
                  placeListHelp(list.tail, next :: buildList, next, place + 1)
                } else {
                  if(place >= 11) buildList
                  else {
                    val next = (place, x._1, x._2)
                    placeListHelp(list.tail, next :: buildList, next, place + 1)
                  }
                }
              }
            }
          }
          placeListHelp(listSorted.tail, List(first), first, 2).sortWith(_._1 < _._1)
        }
      }
    }
    
    val pm: ScalaPersistenceManager = DataStore.pm
    if (!testDate.isEmpty) {
      val cand = QTest.candidate
      val testListDateAndDistrict = pm.query[Test].filter(cand.testDate.eq(testDate.get)).executeList().filter(
          (t: Test) => t.studentId.schoolId.district.eq(this))
      val grades = List(9, 10, 11, 12)
      grades.map((grade: Int) => 
          (grade, 
           makePlaceList(testListDateAndDistrict.filter((t: Test) => t.studentId.grade == grade).map(
             (t: Test) => (t.studentId, new BigDecimal(t.score))).sortBy(_._2).reverse)
          )
      )
    } else {
      val cand = QStudentId.candidate
      val varble = QSchoolId.variable("district")
      val testListDistrict = pm.query[StudentId].filter(cand.schoolId.eq(varble).and(varble.district.eq(this))).executeList()
      val grades = List(9, 10, 11, 12)
      grades.map((grade: Int) => 
          (grade, 
           makePlaceList(testListDistrict.filter((s: StudentId) => s.grade == grade).map(
             (s: StudentId) => (s, s.getCumulativeScore)).sortBy(_._2).reverse)
          )
      )
    }
  }
  
  override def toString: String = "%s: %s".format(year.slug, glmlId)
}

object District {
  def getOrCreateAnswerDistrict(year: Option[Year]): District = {
    null //TODO
  }
}

trait QDistrict extends PersistableExpression[District] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _glmlId: StringExpression = new StringExpressionImpl(this, "_glmlId")
  def name: StringExpression = _glmlId
  
  private[this] lazy val _year: ObjectExpression[Year] = new ObjectExpressionImpl[Year](this, "_year")
  def term: ObjectExpression[Year] = _year
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



