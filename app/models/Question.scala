package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable(detachable="true")
@Unique(name="TEST_WITH_NUMBER", members=Array("_test", "_number"))
class Question {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _number: Int = _
  @Column(length=1)
  private[this] var _answer: String = _
  private[this] var _test: Test = _
  
  def this(number: Int, answer: String, test: Test) = {
    this()
    number_=(number)
    answer_=(answer)
    test_=(test)
  }
  
  def id: Long = _id
  
  def number: Int = _number
  def number_=(theNumber: Int) { _number = theNumber }
  
  def answer: String = _answer
  def answer_=(theAnswer: String) { _answer = theAnswer }
  
  def test: Test = _test
  def test_=(theTest: Test) { _test = theTest }
  
  override def toString: String = "%s %d %s".format(test, 
      number, if (answer == "") "(blank)" else answer)
}

trait QQuestion extends PersistableExpression[Question] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _number: NumericExpression[Int] = new NumericExpressionImpl[Int](this, "_number")
  def number: NumericExpression[Int] = _number

  private[this] lazy val _answer: StringExpression = new StringExpressionImpl(this, "_answer")
  def answer: StringExpression = _answer
  
  private[this] lazy val _test: ObjectExpression[Test] = new ObjectExpressionImpl[Test](this, "_test")
  def test: ObjectExpression[Test] = _test
}

object QQuestion {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QQuestion = {
    new PersistableExpressionImpl[Question](parent, name) with QQuestion
  }
  
  def apply(cls: Class[Question], name: String, exprType: ExpressionType): QQuestion = {
    new PersistableExpressionImpl[Question](cls, name, exprType) with QQuestion
  }
  
  private[this] lazy val jdoCandidate: QQuestion = candidate("this")
  
  def candidate(name: String): QQuestion = QQuestion(null, name, 5)
  
  def candidate(): QQuestion = jdoCandidate
  
  def parameter(name: String): QQuestion = QQuestion(classOf[Question], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QQuestion = QQuestion(classOf[Question], name, ExpressionType.VARIABLE)
}
