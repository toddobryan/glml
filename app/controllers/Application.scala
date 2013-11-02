package controllers

import play.api._
import play.api.mvc._
import models.auth._

import models._

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

import org.dupontmanual.forms
import forms._
import forms.fields._
import forms.widgets.Textarea

import util.Email

import org.joda.time.{DateTime, LocalDate, IllegalFieldValueException}

object Application extends Controller {
  
  def index(maybeDate: Option[LocalDate] = None) = VisitAction { implicit req =>
    val pm = DataStore.pm
    val testDateCand = QTestDate.candidate
    
    val workingYear = req.visit.workingYear
    
    def createData(testDate: Option[TestDate]) = {
      val districtCand = QDistrict.candidate
      val answerKeyDistrictId = StudentId.answerKeyStudentId.substring(0, 1)
      val districtList = pm.query[District].filter(districtCand.year.eq(workingYear.getOrElse(null))).executeList() filterNot (_.glmlId == answerKeyDistrictId)
      val dataList = districtList map { district =>
        DistrictInfo(district, district.getTopSchools(testDate), district.getTopStudents(testDate))
      }
      val testDates = pm.query[TestDate].filter(testDateCand.year.eq(workingYear.getOrElse(null))).executeList()
      Ok(views.html.index(testDate, testDates, dataList))
    }
    
    //date.toDate might not be what I'm looking for
    maybeDate match {
      case Some(date) => pm.query[TestDate].filter(testDateCand.date.eq(date.toDate)).executeOption() match {
        case Some(testDate) => {
          if (testDate.year != workingYear) {
            req.visit.workingYear_=(Some(testDate.year))
            Redirect(routes.Application.indexWithDate(maybeDate.get.getYear, maybeDate.get.getMonthOfYear, maybeDate.get.getDayOfMonth))
          }
          createData(Some(testDate))
        }
        case None => Redirect(routes.Application.index()).flashing("error" -> "That's not a valid date!")
      }
      case None => createData(None)
    }
  }
  
  def indexWithDate(year: Int, month: Int, day: Int) = {
    try {
      index(Some(new LocalDate(year, month, day)))
    } catch {
      case e: IllegalFieldValueException => VisitAction { implicit req =>
        Redirect(routes.Application.index()).flashing("error" -> "That's not a valid date!")
      }
    }
  }
  
  object SendEmailForm extends Form {
    import DataStore.pm
    val coachAndSchool = pm.query[School].executeList().flatMap {
      s => { for (c <- s.coaches) yield (c, s.name) }
    }
    val emailOptions = coachAndSchool.map(t => (s"${t._1.fullName} - ${t._2}", t._1.email)
       // Check that emails are defined 
       ).filter(_._2.isDefined
       // Convert Option[String] to String
       ).map(t => (t._1, t._2.get))
    val email = new ChoiceFieldMultiple[String]("Email", emailOptions)
    val subject = new TextField("subject")
    val message = new TextField("message") { override def widget = new Textarea(true) }
    
    val fields = List(email, subject, message)
  }
  
  def sendEmail = Authenticated { authReq => 
    implicit val req = authReq.vrequest
    Ok(views.html.emailForm(Binding(SendEmailForm))) 
  }
  
  def sendEmailP = Authenticated { authReq =>
    implicit val req = authReq.vrequest
    implicit val user = authReq.user
    Binding(SendEmailForm, req) match {
      case ib: InvalidBinding => Ok(views.html.emailForm(ib))
      case vb: ValidBinding => {
        val recipients = vb.valueOf(SendEmailForm.email).toArray
        val subject = vb.valueOf(SendEmailForm.subject)
        val message = vb.valueOf(SendEmailForm.message)
        val senderInfo = user.fullName + { user.email match { case None => "."; case Some(e) => s" at $e."} }
        Email.sendEmail(senderInfo, recipients, subject, message)
        Redirect(routes.Application.sendEmail).flashing(("success" -> "Email Sent!"))
      }
    }
  }
  
  object NewStudentForm extends Form {
    val firstName = new TextField("First Name")
    val lastName = new TextField("Last Name")
    val grade = new ChoiceField("Grade", List(("9", 9), ("10", 10), ("11", 11), ("12", 12)), false)
    val fields = List(firstName, lastName, grade)
  }
  
  class PromoteStudentForm(val user: User) extends Form {
    val currSchoolId = SchoolId.getCurrentSchoolId(user)
    val studList = Year.lastYear match {
      case None => Nil
      case Some(y) => currSchoolId.getStudentsForYear(y).filter(
    		  			stu => !currSchoolId.getCurrentStudentIds.exists(s => s.student == stu.student)
    		  			).map(
    		  			stu => {
    		  			  import stu.student
    		  			  val str = student.firstName + student.lastName + " - " + stu.glmlId
    		  			  (str, stu)
    		  			})
    }
    val oldStudents = new ChoiceField("Last Years Students", studList, true)
    
    val fields = List(oldStudents)
  }
  
  def roster = Authenticated { implicit auth => 
    val user = auth.user
    implicit val req = auth.vrequest
    val schoolId = SchoolId.getCurrentSchoolId(user)
    val currentStudents = schoolId.getCurrentStudentIds
    Ok(views.html.roster(currentStudents, Binding(NewStudentForm), Binding(new PromoteStudentForm(user))))
  }
  
  def addNewStudent = Authenticated { implicit auth =>
    implicit val (req, user) = (auth.vrequest, auth.user)
    val schoolId = SchoolId.getCurrentSchoolId(user)
    val currentStudents = schoolId.getCurrentStudentIds
    Binding(NewStudentForm, req) match {
      case ib: InvalidBinding => Ok(views.html.roster(currentStudents, ib, Binding(new PromoteStudentForm(user))))
      case vb: ValidBinding => {
        import NewStudentForm._
        val stud = new Student(vb.valueOf(lastName), vb.valueOf(firstName), None, None)
        val newStudent = new StudentId(stud, schoolId, vb.valueOf(grade))
        DataStore.pm.makePersistent(newStudent)
        Redirect(routes.Application.roster).flashing("success" -> "Successfully Added!")
      }
    }
  }
  
  def promoteStudent = Authenticated { implicit auth => 
    implicit val (req, user) = (auth.vrequest, auth.user)
    val schoolId = SchoolId.getCurrentSchoolId(user)
    val currentStudents = schoolId.getCurrentStudentIds
    Binding(new PromoteStudentForm(user), req) match {
      case ib: InvalidBinding => Ok(views.html.roster(currentStudents, Binding(NewStudentForm), ib))
      case vb: ValidBinding => {
        val student: StudentId = vb.valueOf((new PromoteStudentForm(user)).oldStudents)
        DataStore.pm.makePersistent(student.promoted)
        Redirect(routes.Application.roster).flashing("success" -> "Successfully Added!")
      }
    }
  }
  
  def schoolInfo(schoolId: String) = VisitAction { implicit req => 
    val maybeSchool = SchoolId.getByGlmlId(schoolId)
    maybeSchool match {
      case Some(sid) => Ok(views.html.schoolDisplay(sid))
      case None => Redirect(routes.Application.index).flashing(("error" -> "School Not Found"))
    }
  }
  
  def studentInfo(studentId: String) = VisitAction { implicit req => 
    val maybeStudent = StudentId.getByGlmlId(studentId)
    maybeStudent match {
      case Some(sid) => Ok(views.html.studentDisplay(sid))
      case None => Redirect(routes.Application.index).flashing(("error" -> "Student Not Found"))
    }
  }
}

case class DistrictInfo(district: District, schools: List[(String, BigDecimal, Set[String])], students: List[(Int, List[(Int, StudentId, BigDecimal)])])