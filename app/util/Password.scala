package util

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

//TODO: this should be stored as a hash, not plain text
//TODO: "" means no password, so shouldn't match anything

@PersistenceCapable(detachable="true")
class Password {
  private[this] var _value: String = _
  
  def value = _value
  def value_=(theValue: String) { _value = theValue }
  
  def this(value: String) {
    this()
    value_=(value)
  }
  
  def matches(possPass: String): Boolean = {
    value == possPass
  }
}

trait QPassword extends PersistableExpression[Password] {
  private[this] lazy val _value: StringExpression = {
    new StringExpressionImpl(this, "_value")
  }
  def value: StringExpression = _value
}

object QPassword {
  def apply(parent: PersistableExpression[Password], name: String, depth: Int): QPassword = {
    new PersistableExpressionImpl[Password](parent, name) with QPassword
  }
  
  def apply(cls: Class[Password], name: String, exprType: ExpressionType): QPassword = {
    new PersistableExpressionImpl[Password](cls, name, exprType) with QPassword
  }
  
  private[this] lazy val jdoCandidate: QPassword = candidate("this")
  
  def candidate(name: String): QPassword = candidate("this")
  
  def candidate(): QPassword = jdoCandidate
  
  def parameter(name: String): QPassword = QPassword(classOf[Password], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QPassword = QPassword(classOf[Password], name, ExpressionType.VARIABLE)
}