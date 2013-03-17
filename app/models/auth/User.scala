package models.auth

import scala.collection.mutable
import scala.collection.JavaConverters._
import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import scalajdo.DataStore

@PersistenceCapable(detachable="true")
class User {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  def id: Long = _id
  
  @Unique
  @Column(allowsNull="false")
  private[this] var _username: String = _
  def username: String = _username
  def username_=(theUsername: String) { _username = theUsername }
  
  private[this] var _first: String = _
  def first: Option[String] = Option(_first)
  def first_=(theFirst: Option[String]) { _first = theFirst.getOrElse(null) }
  def first_=(theFirst: String) { _first = theFirst }
  
  private[this] var _last: String = _
  def last: Option[String] = Option(_last)
  def last_=(theLast: Option[String]) { _last = theLast.getOrElse(null) }
  def last_=(theLast: String) { _last = theLast }
  
  @Column(allowsNull="false")
  private[this] var _isActive: Boolean = _
  def isActive: Boolean = _isActive
  def isActive_=(theIsActive: Boolean) { _isActive = theIsActive }
  
  @Column(allowsNull="false")
  private[this] var _isSuperUser: Boolean = _
  def isSuperUser: Boolean = _isSuperUser
  def isSuperUser_=(theIsSuperUser: Boolean) { _isSuperUser = theIsSuperUser }
  
  @Persistent(defaultFetchGroup="true")
  @Column(allowsNull="false")
  private[this] var _dateJoined: java.sql.Timestamp = _
  def dateJoined: DateTime = new DateTime(_dateJoined.getTime)
  def dateJoined_=(theDateJoined: DateTime) { _dateJoined = new java.sql.Timestamp(theDateJoined.getMillis) }
  
  @Persistent(defaultFetchGroup="true")
  private[this] var _lastLogin: java.sql.Timestamp = _
  def lastLogin: Option[DateTime] = if (_lastLogin == null) None else Some(new DateTime(_lastLogin.getTime))
  def lastLogin_=(theLastLogin: DateTime) { _lastLogin = new java.sql.Timestamp(theLastLogin.getMillis) }
  
  //TODO: check email validity
  private[this] var _email: String = _
  def email: Option[String] = Option(_email)
  def email_=(theEmail: Option[String]) { _email = theEmail.getOrElse(null) }
  def email_=(theEmail: String) { _email = theEmail }
  
  // Uses BCrypt for hashing
  // TODO: allow other hashes
  private[this] var _passwordHash: String = _
  def setPassword(password: String) { 
    if (password == null) _passwordHash = null
    else _passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
  }
  def passwordChecks(password: String): Boolean = (_passwordHash != null) && BCrypt.checkpw(password, _passwordHash)
  
  @Join
  private[this] var _permissions: java.util.Set[Permission] = _
  def permissions: mutable.Set[Permission] = _permissions.asScala
  def permissions_=(thePermissions: mutable.Set[Permission]) { _permissions = thePermissions.asJava }
  
  @Join
  private[this] var _groups: java.util.Set[Group] = _
  def groups: mutable.Set[Group] = _groups.asScala
  def groups_=(theGroups: mutable.Set[Group]) { _groups = theGroups.asJava }
  
  def this(username: String, first: String = null, last: String = null, isActive: Boolean = true, isSuperUser: Boolean = false,
      dateJoined: => DateTime = DateTime.now(), lastLogin: Option[DateTime] = None, email: String = null, password: String = null) {
    this()
    username_=(username)
    first_=(first)
    last_=(last)
    isActive_=(isActive)
    isSuperUser_=(isSuperUser)
    dateJoined_=(dateJoined)
    lastLogin match {
      case None => _lastLogin = null
      case Some(date) => lastLogin_=(date)
    }
    email_=(email)
    setPassword(password)
    permissions_=(mutable.Set[Permission]())
  }
  
  def fullName(): String = first.getOrElse("(no first name entered)") + " " + last.getOrElse("(no last name entered)")
}

object User {
  def getByUsername(username: String): Option[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].filter(cand.username.eq(username)).executeOption()
  }
  
  def authenticate(username: String, password: String): Option[User] = {
    getByUsername(username).filter(_.passwordChecks(password))
  }
}

trait QUser extends PersistableExpression[User] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _username: StringExpression = new StringExpressionImpl(this, "_username")
  def username: StringExpression = _username
  
  private[this] lazy val _first: StringExpression = new StringExpressionImpl(this, "_first")
  def first: StringExpression = _first
  
  private[this] lazy val _last: StringExpression = new StringExpressionImpl(this, "_last")
  def last: StringExpression = _last
    
  private[this] lazy val _email: StringExpression = new StringExpressionImpl(this, "_email")
  def email: StringExpression = _email
}

object QUser {
  def apply(parent: PersistableExpression[User], name: String, depth: Int): QUser = {
    new PersistableExpressionImpl[User](parent, name) with QUser
  }
  
  def apply(cls: Class[User], name: String, exprType: ExpressionType): QUser = {
    new PersistableExpressionImpl[User](cls, name, exprType) with QUser
  }
  
  private[this] lazy val jdoCandidate: QUser = candidate("this")
  
  def candidate(name: String): QUser = QUser(null, name, 5)
  
  def candidate(): QUser = jdoCandidate

  def parameter(name: String): QUser = QUser(classOf[User], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QUser = QUser(classOf[User], name, ExpressionType.VARIABLE)

}


