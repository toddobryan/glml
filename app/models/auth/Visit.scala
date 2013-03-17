package models.auth

import java.util.UUID
import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.DataStore

@PersistenceCapable(detachable="true")
class Visit {
  @PrimaryKey
  var _uuid: String = UUID.randomUUID().toString()
  def uuid: String = _uuid
  
  var _expiration: Long = _
  def expiration: Long = _expiration
  def expiration_=(theExpiration: Long) { _expiration = theExpiration }
  
  var _user: User = _
  def user: Option[User] = Option(_user)
  def user_=(theUser: Option[User]) { _user = theUser.getOrElse(null) }
  def user_=(theUser: User) { _user = theUser }
  
  var _redirectUrl: String = _
  def redirectUrl: Option[String] = Option(_redirectUrl)
  def redirectUrl_=(theRedirectUrl: Option[String]) { _redirectUrl = theRedirectUrl.getOrElse(null) }
  def redirectUrl_=(theRedirectUrl: String) { _redirectUrl = theRedirectUrl }
  
  def this(expiration: Long, maybeUser: Option[User]) = {
    this()
    expiration_=(expiration)
    user_=(maybeUser)
    
  }
  
  def isExpired: Boolean = System.currentTimeMillis > expiration
}

object Visit {
  val visitLength = 3600000 // millis in an hour
  var nextCleanup = System.currentTimeMillis + visitLength
  
  def getByUuid(uuid: String): Option[Visit] = {
    if (System.currentTimeMillis > nextCleanup) {
      nextCleanup = System.currentTimeMillis + visitLength
      Visit.deleteExpired()
    }
    val cand = QVisit.candidate
    DataStore.pm.query[Visit].filter(cand.uuid.eq(uuid)).executeOption()
  }
  
  def deleteExpired() {
    val cand = QVisit.candidate
    val pm = DataStore.pm
    val expiredVisits = pm.query[Visit].filter(cand.expiration.lt(System.currentTimeMillis)).executeList()
    pm.deletePersistentAll(expiredVisits)
  }
}

trait QVisit extends PersistableExpression[Visit] {
  private[this] lazy val _uuid: StringExpression = new StringExpressionImpl(this, "_uuid")
  def uuid: StringExpression = _uuid
  
  private[this] lazy val _expiration: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_expiration")
  def expiration: NumericExpression[Long] = _expiration
  
  private[this] lazy val _user: ObjectExpression[User] = new ObjectExpressionImpl[User](this, "_user")
  def user: ObjectExpression[User] = _user
}

object QVisit {
  def apply(parent: PersistableExpression[Visit], name: String, depth: Int): QVisit = {
    new PersistableExpressionImpl[Visit](parent, name) with QVisit
  }
  
  def apply(cls: Class[Visit], name: String, exprType: ExpressionType): QVisit = {
    new PersistableExpressionImpl[Visit](cls, name, exprType) with QVisit
  }
  
  private[this] lazy val jdoCandidate: QVisit = candidate("this")
  
  def candidate(name: String): QVisit = QVisit(null, name, 5)
  
  def candidate(): QVisit = jdoCandidate

  def parameter(name: String): QVisit = QVisit(classOf[Visit], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QVisit = QVisit(classOf[Visit], name, ExpressionType.VARIABLE)
}
