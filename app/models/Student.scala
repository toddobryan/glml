package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

import auth.User

@PersistenceCapable(detachable="true")
@Unique(name="NAME", members=Array("_lastName", "_firstName", "_middleName"))
class Student extends Ordered[Student] {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Column(allowsNull="false", length=50)
  private[this] var _lastName: String = _
  @Column(allowsNull="false", length=50)
  private[this] var _firstName: String = _
  @Column(allowsNull="true", length=50)
  private[this] var _middleName: String = _
  @Column(allowsNull="true", length=50)
  private[this] var _suffix: String = _
  
  def this(lastName: String, firstName: String,
           middleName: Option[String] = None, suffix: Option[String] = None) = {
    this()
    lastName_=(lastName)
    firstName_=(firstName)
    middleName_=(middleName)
    suffix_=(suffix)
  }
  
  def lastName: String = _lastName
  def lastName_=(theLastName: String) { _lastName = theLastName }
  
  def firstName: String = _firstName
  def firstName_=(theFirstName: String) { _firstName = theFirstName }
  
  def middleName: Option[String] = if (_middleName == null) None else Some(_middleName)
  def middleName_=(theMiddleName: Option[String]) {
    _middleName = theMiddleName.getOrElse(null)
  }
  
  def suffix: Option[String] = if (_suffix == null) None else Some(_suffix)
  def suffix_=(theSuffix: Option[String]) {
    _suffix = theSuffix.getOrElse(null)
  }
  
  def isKey: Boolean = {
    lastName == Student.answerKeyStudentName &&
    firstName == Student.answerKeyStudentName &&
    middleName == Student.answerKeyStudentName &&
    suffix == Student.answerKeyStudentName
  }
  
  def compare(that: Student):Int = {
    import Ordering._
    Ordering.Tuple3(Ordering.String, Ordering.String, Ordering.Option[String]).compare(
        (lastName, firstName, middleName), 
        (that.lastName, that.firstName, that.middleName))
  }
  
  def isCoachedBy(coach: User): Boolean = {
    false //TODO
  }
  
  /*
   def coached_by(self, coach):
        for student_id in self.studentid_set.all():
            if coach in student_id.school_id.school.coaches():
                return True
        return coach.is_superuser
   */
  
  def name: String = toString
  
  override def toString: String = {
    "%s, %s%s%s".format(lastName, firstName, 
        if (middleName.isDefined) " " + middleName.get else "",
        if (suffix.isDefined) " " + suffix.get else "")       
  }
}

object Student {
  val answerKeyStudentName = "KEY"

  def getOrCreateAnswerKeyStudent: Student = {
    DataStore.pm.query[Student].executeList().filter(_.isKey) match {
      case x :: xs => x
      case Nil => {
        val newStudent = new Student(Student.answerKeyStudentName, Student.answerKeyStudentName,
            Some(Student.answerKeyStudentName), Some(Student.answerKeyStudentName))
        DataStore.pm.makePersistent(newStudent)
        newStudent
      }
    }
  }
}