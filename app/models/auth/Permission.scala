package models.auth

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

class Permission {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  def id: Long = _id
  
  @Persistent(defaultFetchGroup="true")
  private[this] var _contentType: Class[_] = _
  def contentType: Class[_] = _contentType
  def contentType_=(theContentType: Class[_]) { _contentType = theContentType }
  
  @Unique
  @Column(allowsNull="false")
  private[this] var _name: String = _
  def name: String = _name
  def name_=(theName: String) { _name = theName }
}

object Permission {
  
}

trait QPermission extends PersistableExpression[Permission] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _contentType: ObjectExpression[Class[_]] = new ObjectExpressionImpl[Class[_]](this, "_contentType")
  def contentType: ObjectExpression[Class[_]] = _contentType

  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
}

object QPermission {
  def apply(parent: PersistableExpression[Permission], name: String, depth: Int): QPermission = {
    new PersistableExpressionImpl[Permission](parent, name) with QPermission
  }
  
  def apply(cls: Class[Permission], name: String, exprType: ExpressionType): QPermission = {
    new PersistableExpressionImpl[Permission](cls, name, exprType) with QPermission
  }
  
  private[this] lazy val jdoCandidate: QPermission = candidate("this")
  
  def candidate(name: String): QPermission = QPermission(null, name, 5)
  
  def candidate(): QPermission = jdoCandidate

  def parameter(name: String): QPermission = QPermission(classOf[Permission], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QPermission = QPermission(classOf[Permission], name, ExpressionType.VARIABLE)

}