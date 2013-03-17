package models.auth

import scala.collection.mutable
import scala.collection.JavaConverters._

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable(detachable="true")
class Group {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  def id: Long = _id
  
  @Unique
  @Column(allowsNull="false")
  private[this] var _name: String = _
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  @Join
  private[this] var _permissions: java.util.Set[Permission] = _
  def permissions: mutable.Set[Permission] = _permissions.asScala
  def permissions_=(thePermissions: mutable.Set[Permission]) { _permissions = thePermissions.asJava }
}

object Group {
  
}

trait QGroup extends PersistableExpression[Group] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _permissions: CollectionExpression[java.util.Set[Permission], Permission] = 
    new CollectionExpressionImpl[java.util.Set[Permission], Permission](this, "_permissions")
  def permissions: CollectionExpression[java.util.Set[Permission], Permission] = _permissions
}

object QGroup {
  def apply(parent: PersistableExpression[Group], name: String, depth: Int): QGroup = {
    new PersistableExpressionImpl[Group](parent, name) with QGroup
  }
  
  def apply(cls: Class[Group], name: String, exprType: ExpressionType): QGroup = {
    new PersistableExpressionImpl[Group](cls, name, exprType) with QGroup
  }
  
  private[this] lazy val jdoCandidate: QGroup = candidate("this")
  
  def candidate(name: String): QGroup = QGroup(null, name, 5)
  
  def candidate(): QGroup = jdoCandidate

  def parameter(name: String): QGroup = QGroup(classOf[Group], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QGroup = QGroup(classOf[Group], name, ExpressionType.VARIABLE)

}