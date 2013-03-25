package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore
import java.math.BigDecimal

@PersistenceCapable(detachable="true")
@Uniques(Array(
    new Unique(name="SCHOOL_ID_WITH_STUDENT", members=Array("_schoolId", "_student")),
    new Unique(name="SCHOOL_ID_WITH_GLML_ID", members=Array("_schoolId", "_glmlId"))))
class StudentId {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)  
  private[this] var _id: Long = _
  @Column(allowsNull="false", length=6)
  private[this] var _glmlId: String = _
  private[this] var _student: Student = _
  private[this] var _schoolId: SchoolId = _
  private[this] var _grade: Int = _
  
  def this(glmlId: String, student: Student, schoolId: SchoolId, grade: Int) {
    this()
    glmlId_=(glmlId)
    student_=(student)
    schoolId_=(schoolId)
    grade_=(grade)
  }
  
  def id: Long = _id
  
  def glmlId: String = _glmlId
  def glmlId_=(theId: String) { _glmlId = theId }
  
  def student: Student = _student
  def student_=(theStudent: Student) { _student = theStudent }
  
  def schoolId: SchoolId = _schoolId
  def schoolId_=(theSchoolId: SchoolId) { _schoolId = theSchoolId }
  
  def grade: Int = _grade
  def grade_=(theGrade: Int) { _grade = theGrade }
  
  def getCumulativeScore: BigDecimal= {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QTest.candidate
    pm.query[Test].filter(cand.studentId.eq(this)).executeList().foldRight[BigDecimal](new BigDecimal(0.0))(
        (t: Test, sum: BigDecimal) => new BigDecimal(t.score).add(sum))
  }
  
  override def toString: String = "%s: %s (%s)".format(schoolId.district.year.slug, student, glmlId)		  
}

object StudentId {
  def getOrCreateAnswerStudentId(year: Option[Year] = None): StudentId = {
    null //TODO
  }
}

trait QStudentId extends PersistableExpression[StudentId] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _glmlId: StringExpression = new StringExpressionImpl(this, "_glmlId")
  def glmlId: StringExpression = _glmlId

  private[this] lazy val _student: ObjectExpression[Student] = new ObjectExpressionImpl[Student](this, "_student")
  def student: ObjectExpression[Student] = _student
  
  private[this] lazy val _schoolId: ObjectExpression[SchoolId] = new ObjectExpressionImpl[SchoolId](this, "_school")
  def school: ObjectExpression[SchoolId] = _schoolId
  
  private[this] lazy val _grade: NumericExpression[Int] = new NumericExpressionImpl[Int](this, "_grade")
  def grade: NumericExpression[Int] = _grade
}

object QStudentId {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QStudentId = {
    new PersistableExpressionImpl[StudentId](parent, name) with QStudentId
  }
  
  def apply(cls: Class[StudentId], name: String, exprType: ExpressionType): QStudentId = {
    new PersistableExpressionImpl[StudentId](cls, name, exprType) with QStudentId
  }
  
  private[this] lazy val jdoCandidate: QStudentId = candidate("this")
  
  def candidate(name: String): QStudentId = QStudentId(null, name, 5)
  
  def candidate(): QStudentId = jdoCandidate
  
  def parameter(name: String): QStudentId = QStudentId(classOf[StudentId], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QStudentId = QStudentId(classOf[StudentId], name, ExpressionType.VARIABLE)
}

