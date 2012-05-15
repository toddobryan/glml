package models

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

import org.joda.time.DateTime

import util.Email
import util.Password

@PersistenceCapable(detachable="true")
class User {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Unique
  @Column(allowsNull="false", length=30)
  private[this] var _username: String = _
  @Column(allowsNull="true", length=30)
  private[this] var _firstName: String = _
  @Column(allowsNull="true", length=30)
  private[this] var _lastName: String = _
  @Persistent(defaultFetchGroup="true")
  @Embedded
  @Unique
  @Column(allowsNull="true")
  private[this] var _email: Email = _
  @Persistent(defaultFetchGroup="true")
  @Embedded
  @Column(allowsNull="true")
  private[this] var _password: Password = _
  private[this] var _isStaff: Boolean = _
  private[this] var _isActive: Boolean = _
  private[this] var _isSuperuser: Boolean = _
  @Persistent(defaultFetchGroup="true")
  @Column(allowsNull="true")
  private[this] var _lastLogin: DateTime = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _dateJoined: DateTime = _
  
  def this(username: String, firstName: Option[String], lastName: Option[String],
           email: Option[String], password: Option[String]) = {
    this()
    username_=(username)
    firstName_=(firstName)
    lastName_=(lastName)
    email_=(email)
    password_=(password)
    _dateJoined = new DateTime()
  }
  
  def id: Long = _id
  
  def username: String = _username
  def username_=(theUsername: String) { _username = theUsername }
  
  def firstName: Option[String] = if (_firstName == null) None else Some(_firstName)
  def firstName_=(theFirstName: Option[String]) { _firstName = theFirstName.getOrElse(null) }
  
  def lastName: Option[String] = if (_lastName == null) None else Some(_lastName)
  def lastName_=(theLastName: Option[String]) { _lastName = theLastName.getOrElse(null) }
  
  def email: Option[String] = if (_email == null) None else Some(_email.value)
  def email_=(theEmail: Option[String]) {
    _email = if (theEmail.isDefined) new Email(theEmail.get) 
             else null
  }
  
  def password: Password = _password
  def password_=(thePassword: Option[String]) { _password = new Password(thePassword.getOrElse("")) }
  
  def isStaff: Boolean = _isStaff
  def setIsStaff(staff: Boolean) { _isStaff = staff }
  
  def isActive: Boolean = _isActive
  def setIsActive(active: Boolean) { _isActive = active }
  
  def isSuperuser: Boolean = _isSuperuser
  def setIsSuperuser(superuser: Boolean) = { _isSuperuser = superuser }
  
  def dateJoined: DateTime = _dateJoined
  
  def lastLogin: Option[DateTime] = if (_lastLogin == null) None else Some(_lastLogin)
  def lastLogin_=(theLastLogin: DateTime) { _lastLogin = theLastLogin }
  
  def getFullName: String = "%s %s".format(firstName.getOrElse(""), lastName.getOrElse(""))
}

trait QUser extends PersistableExpression[User] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
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