package models

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._
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
  private[this] var _email: Email = _
  @Persistent(defaultFetchGroup="true")
  @Embedded
  private[this] var _password: Password = _
  private[this] var _isStaff: Boolean = _
  private[this] var _isActive: Boolean = _
  private[this] var _isSuperuser: Boolean = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _lastLogin: java.sql.Timestamp = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _dateJoined: java.sql.Timestamp = _
}