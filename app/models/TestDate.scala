package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import org.joda.time.{DateTime, LocalDate}

@PersistenceCapable(detachable="true")
class TestDate {
  private[this] var _id: Int = _
  @Persistent(defaultFetchGroup="true")
  @Unique
  private[this] var _date: java.sql.Date = _
  private[this] var _year: Year = _
  
  def id: Int = _id
  
  def date: LocalDate = new DateTime(_date).toLocalDate
  def date_=(theDate: LocalDate) { 
    _date = new java.sql.Date(theDate.toDateTimeAtStartOfDay.toDate.getTime)
  }
  
  def year: Year = _year
  
  override def toString: String = "%s: %s".format(year.slug, date)
}

object TestDate {
  def importTest(data: Object) {
    //TODO
  }
}

trait QTestDate extends PersistableExpression[TestDate] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _date: DateExpression[java.util.Date] = new DateExpressionImpl[java.util.Date](this, "_date")
  
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
