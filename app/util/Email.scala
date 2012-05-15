package util

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

// TODO: verify that it's a valid email address

@PersistenceCapable(detachable="true")
class Email {
	private[this] var _value: String = _
	
	def value = _value
	def value_=(theValue: String) { _value = theValue }
		
	def this(value: String) {
	    this()
		value_=(value)
	}
}

trait QEmail extends PersistableExpression[Email] {
  private[this] lazy val _value: StringExpression = {
    new StringExpressionImpl(this, "_value")
  }
  def value: StringExpression = _value
}

object QEmail {
  def apply(parent: PersistableExpression[Email], name: String, depth: Int): QEmail = {
    new PersistableExpressionImpl[Email](parent, name) with QEmail
  }
  
  def apply(cls: Class[Email], name: String, exprType: ExpressionType): QEmail = {
    new PersistableExpressionImpl[Email](cls, name, exprType) with QEmail
  }

  private[this] lazy val jdoCandidate: QEmail = candidate("this")
  
  def candidate(name: String): QEmail = QEmail(null, name, 5)
  
  def candidate(): QEmail = jdoCandidate
  
  def parameter(name: String): QEmail = QEmail(classOf[Email], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QEmail = QEmail(classOf[Email], name, ExpressionType.VARIABLE)
}
