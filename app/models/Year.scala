package models

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._
import scalajdo.DataStore

@PersistenceCapable(detachable="true")
class Year {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Unique
  @Column(allowsNull="false")
  private[this] var _start: Int = _
  
  def this(start: Int) = {
    this()
    start_=(start)
  }
  
  def id: Long = _id
  
  def start: Int = _start
  def start_=(theStart: Int) { _start = theStart }
  
  def end: Int = start + 1
  
  def slug: String = "%s%s".format(start.toString.drop(2), end.toString.drop(2))
    
  override def toString = "%s-%s".format(start, end)
}

object Year {
  def currentYear: Year = {
    val pm = DataStore.pm
    val cand = QYear.candidate
    val years: List[Year] = pm.query[Year].orderBy(cand.start.desc).executeList()
    if (!years.isEmpty) years(0) else throw new Exception("There is no current year!")
  }
}

trait QYear extends PersistableExpression[Year] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _start: NumericExpression[Int] = new NumericExpressionImpl[Int](this, "_start")
  def start: NumericExpression[Int] = _start
}

object QYear {
  def apply(parent: PersistableExpression[Year], name: String, depth: Int): QYear = {
    new PersistableExpressionImpl[Year](parent, name) with QYear
  }
  
  def apply(cls: Class[Year], name: String, exprType: ExpressionType): QYear = {
    new PersistableExpressionImpl[Year](cls, name, exprType) with QYear
  }
  
  private[this] lazy val jdoCandidate: QYear = candidate("this")
  
  def candidate(name: String): QYear = QYear(null, name, 5)
  
  def candidate(): QYear = jdoCandidate

  def parameter(name: String): QYear = QYear(classOf[Year], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QYear = QYear(classOf[Year], name, ExpressionType.VARIABLE)

}
