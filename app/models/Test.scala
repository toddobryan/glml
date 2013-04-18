package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

@PersistenceCapable(detachable="true")
@Unique(name="TEST_DATE_WITH_STUDENT_ID", members=Array("_testDate", "_studentId"))
class Test {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _testDate: TestDate = _
  private[this] var _studentId: StudentId = _
  @Persistent
  @Column(length=4, scale=2)
  private[this] var _score: java.math.BigDecimal = _
  
  def this(testDate: TestDate, studentId: StudentId, score: BigDecimal) = {
    this()
    testDate_=(testDate)
    studentId_=(studentId)
    score_=(score)
  }
  
  def id: Long = _id
  
  def testDate: TestDate = _testDate
  def testDate_=(theTestDate: TestDate){ _testDate = theTestDate }
  
  def studentId: StudentId = _studentId
  def studentId_=(theStudentId: StudentId) { _studentId = theStudentId }
  
  def score: BigDecimal = BigDecimal(_score)
  def score_=(theScore: BigDecimal) { _score = theScore.bigDecimal }
  def score_=(theScore: Double) { score_=(BigDecimal(theScore)) }
  
  def compareMapping(): Map[Any, Any] = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QQuestion.candidate
    val questionMap = pm.query[Question].filter(cand.test.eq(this)).executeList().map((q: Question) => (q.number, q.answer)).toMap
    Map("name" -> studentId.student.name, "questions" -> questionMap)
  }
  
  def rescore(): BigDecimal = {
    
    BigDecimal(0.0) //TODO
  }
  
  /*
    def rescore(self):
        score = 0.0
        key = self.test_date.get_key()
        answer_list = dict([(q.number, q.answer) for q in key.question_set.all()])
        question_list = self.question_set.all()
        for question in question_list:
            if question.number in answer_list:
                correct_answer = answer_list[question.number]
                if question.answer.upper() == correct_answer.upper():
                    #correct
                    if question.number <= 6:
                        score += 2
                    elif question.number <= 12:
                        score += 3
                    elif question.number <= 18:
                        score += 4
                    elif question.number <= 24:
                        score += 5
                elif question.answer == "":
                    # Score is untouched.
                    pass
                else:
                    #incorrect
                    if question.number <= 6:
                        score -= .5
                    elif question.number <= 12:
                        score -= .75
                    elif question.number <= 18:
                        score -= 1.00
                    elif question.number <= 24:
                        score -= 1.25
            else:
                pass
        self.score = unicode(score)
        self.save()
        return score
   */
  
  override def toString: String = "%s %s (%d)".format(testDate, studentId.toString.substring(6), score)
}

trait QTest extends PersistableExpression[Test] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _testDate: ObjectExpression[TestDate] = new ObjectExpressionImpl[TestDate](this, "_testDate")
  def testDate: ObjectExpression[TestDate] = _testDate
  
  private[this] lazy val _studentId: ObjectExpression[StudentId] = new ObjectExpressionImpl[StudentId](this, "_studentId")
  def studentId: ObjectExpression[StudentId] = _studentId
  
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
