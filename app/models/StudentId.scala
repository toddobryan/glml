package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

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
  @Persistent(defaultFetchGroup="true")
  private[this] var _student: Student = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _schoolId: SchoolId = _
  private[this] var _grade: Int = _
  
  def this(glmlId: String, student: Student, schoolId: SchoolId, grade: Int) {
    this()
    glmlId_=(glmlId)
    student_=(student)
    schoolId_=(schoolId)
    grade_=(grade)
  }
  
  def this(student: Student, schoolId: SchoolId, grade: Int) {
    this()
    glmlId_=(StudentId.getGlmlId(schoolId, grade))
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
  
  def name = {student.firstName + " " + student.lastName}
  
  def getCumulativeScore(): BigDecimal = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QTest.candidate
    pm.query[Test].filter(cand.studentId.eq(this)).executeList().foldLeft(BigDecimal(0.0))((sum, test) => test.score + sum)
  }
  
  def promoted: StudentId = { 
    val newGlml = glmlId.substring(0, 3) + (grade - 7) + glmlId.substring(4, 6)
    // Using promoted requires that a current school id currently exists for the school
    // this student belongs to. Otherwise, null
    val currSchoolId = SchoolId.getCurrentSchoolIdBySchool(schoolId.school).getOrElse(null)
    new StudentId(student, currSchoolId, grade + 1) 
  }
  
  override def toString: String = "%s: %s (%s)".format(schoolId.district.year.slug, student, glmlId)		  
}

object StudentId {
  val answerKeyStudentId = "999999"

  def getOrCreateAnswerKeyStudentId(maybeYear: Option[Year] = None): StudentId = {
    val glmlId = StudentId.answerKeyStudentId
    val year = maybeYear.getOrElse(Year.currentYear)
    val student = Student.getOrCreateAnswerKeyStudent
    val schoolId = SchoolId.getOrCreateAnswerKeySchoolId(Some(year))
    val cand = QStudentId.candidate
    DataStore.pm.query[StudentId].filter(cand.student.eq(student).and(
        cand.schoolId.eq(schoolId))).executeOption() match {
      case Some(s) => s
      case None => {
        val newStudentId = new StudentId(glmlId, student, schoolId, 9)
        DataStore.pm.makePersistent(newStudentId)
        newStudentId
      }
    }
  }
  
  def getByGlmlId(studentId: String): Option[StudentId] = {
    val cand = QStudentId.candidate
    DataStore.pm.query[StudentId].filter(cand.glmlId.eq(studentId)).executeOption
  }
  
  def promote(studentId: String): Option[StudentId] = {
    val maybeStudent = getByGlmlId(studentId)
    maybeStudent match {
      case Some(s) => {
        Some(DataStore.pm.makePersistent(s.promoted))
      }
      case None => None
    }
  }
  
  def getGlmlId(schoolId: SchoolId, grade: Int): String = {
    schoolId.district.glmlId + schoolId.glmlId + (grade - 8) + schoolId.getValidId(grade)
  }
}

trait QStudentId extends PersistableExpression[StudentId] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _glmlId: StringExpression = new StringExpressionImpl(this, "_glmlId")
  def glmlId: StringExpression = _glmlId

  private[this] lazy val _student: ObjectExpression[Student] = new ObjectExpressionImpl[Student](this, "_student")
  def student: ObjectExpression[Student] = _student
  
  private[this] lazy val _schoolId: ObjectExpression[SchoolId] = new ObjectExpressionImpl[SchoolId](this, "_schoolId")
  def schoolId: ObjectExpression[SchoolId] = _schoolId
  
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

