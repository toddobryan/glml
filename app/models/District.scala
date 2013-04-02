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
  
  def getTopSchools(testDate: Option[TestDate]): List[(String, BigDecimal, Set[User])] = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QSchoolId.candidate
    if (!testDate.isEmpty) {
      val cand2 = QTest.candidate
      pm.query[SchoolId].filter(cand.district.eq(this)).executeList().map(
          (s: SchoolId) => (s.school.name, pm.query[Test].filter(
          cand2.testDate.eq(testDate.get)).executeList().filter(
          (t: Test) => t.studentId.schoolId.eq(s)).foldRight[BigDecimal](BigDecimal(0.0))(
          (t: Test, sum: BigDecimal) => t.score + sum), s.coaches)).sortBy(_._2).reverse
    } else {
      pm.query[SchoolId].filter(cand.district.eq(this)).executeList().map((s: SchoolId) => (s.school.name, s.getCumulativeScore, s.coaches)).sortBy(_._2).reverse
    }
    
    /* TODO: return coaches' names, not coaches
     return [{'name': school_id.school.name,
                 'score': score,
                 'coaches': school_id.coaches_names()} for (school_id,
                                                            score) in sorted_list]
     */
  }
  
  def getTopStudents(testDate: Option[TestDate]): List[Nothing] = {
    Nil //TODO
  }
  
  /*
   def get_top_students(self, test_date=None):
        from glml.utils import Place, StudentWithScore
        final_list = []
        for grade in GRADES:
            if test_date:
                students = [StudentWithScore(test.student_id, float(test.score)) for test in Test.objects.filter(student_id__school_id__district=self,
                                                                                                                 test_date=test_date,
                                                                                                                 student_id__grade=grade)]
            else:
                students = [StudentWithScore(students_id,
                                             students_id.get_cumulative_score()) for students_id in StudentID.objects.filter(school_id__district=self,
                                                                                                                             grade=grade)]
            students.sort()
            final_list.append({'grade': grade, 'place_list': Place.make_place_list(students)})
        return final_list
   */
  
  /*
   class StudentWithScore(object):
    def __init__(self, student_id, score):
        self.student_id = student_id
        self.score = score
        
    def __cmp__(self, other):
        if self.score == other.score:
            return cmp(self.student_id.student, other.student_id.student)
        else:
            return cmp(other.score, self.score)
        
class Place(object):
    def __init__(self, place, students):
        self.place = place
        self.students = students

    def length(self):
        return len(self.students)

    @staticmethod
    def make_place_list(students):
        place_list = []
        start_index = 0
        end_index = 1
        place = 1
        while place <= 10 and end_index < len(students) and students[start_index].score > 0.0:
            while end_index < len(students) and students[start_index].score == students[end_index].score:
                end_index += 1
            place_students = students[start_index:end_index]
            place_list.append(Place(place, place_students))
            place += len(place_students)
            start_index = end_index
            end_index = start_index + 1
        return place_list
   */
  
  override def toString: String = "District(id=%d)".format(id)
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



