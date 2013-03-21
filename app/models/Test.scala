package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable(detachable="true")
@Unique(name="TEST_DATE_WITH_STUDENT_ID", members=Array("_testDate", "_studentId"))
class Test {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _testDate: TestDate = _
  private[this] var _studentId: StudentId = _
  private[this] var _studentSchoolId: SchoolId = _
  @Persistent
  @Column(length=4, scale=2)
  private[this] var _score: java.math.BigDecimal = _
  
  def this(testDate: TestDate, studentId: StudentId, score: Double) = {
    this()
    testDate_=(testDate)
    studentId_=(studentId)
    score_=(score)
    _studentSchoolId = studentId.schoolId
  }
  
  def id: Long = _id
  
  def testDate: TestDate = _testDate
  def testDate_=(theTestDate: TestDate){ _testDate = theTestDate }
  
  def studentId: StudentId = _studentId
  def studentId_=(theStudentId: StudentId) { _studentId = theStudentId }
  
  def score: Double = _score.doubleValue
  def score_=(theScore: Double) { _score = new java.math.BigDecimal(theScore) }
  
  def compareMapping {} //TODO
  
  def rescore {} //TODO
  
  override def toString: String = "%s %s (%d)".format(testDate, studentId.toString.substring(6), score)
}

trait QTest extends PersistableExpression[Test] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _testDate: ObjectExpression[TestDate] = new ObjectExpressionImpl[TestDate](this, "_testDate")
  def testDate: ObjectExpression[TestDate] = _testDate
  
  private[this] lazy val _studentId: ObjectExpression[StudentId] = new ObjectExpressionImpl[StudentId](this, "_studentId")
  def studentId: ObjectExpression[StudentId] = _studentId
  
  private[this] lazy val _studentSchoolId: ObjectExpression[SchoolId] = new ObjectExpressionImpl[SchoolId](this, "_studentSchoolId")
  def studentSchoolId: ObjectExpression[SchoolId] = _studentSchoolId
  
  private[this] lazy val _score: NumericExpression[java.math.BigDecimal] = new NumericExpressionImpl[java.math.BigDecimal](this, "_score")
  def score: NumericExpression[java.math.BigDecimal] = _score
}

object QTest {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QTest = {
    new PersistableExpressionImpl[Test](parent, name) with QTest
  }
  
  def apply(cls: Class[Test], name: String, exprType: ExpressionType): QTest = {
    new PersistableExpressionImpl[Test](cls, name, exprType) with QTest
  }
  
  private[this] lazy val jdoCandidate: QTest = candidate("this")
  
  def candidate(name: String): QTest = QTest(null, name, 5)
  
  def candidate(): QTest = jdoCandidate
  
  def parameter(name: String): QTest = QTest(classOf[Test], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QTest = QTest(classOf[Test], name, ExpressionType.VARIABLE)
}
