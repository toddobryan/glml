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
    val email = new ChoiceField[String]("Email", emailOptions)
    val subject = new TextField("subject")
    val message = new TextField("message") { override def widget = new Textarea(true) }
    override def legend = Some("Send an Email")
    
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
        val recipients = List(vb.valueOf(SendEmailForm.email)).toArray
        println(recipients.length)
        val subject = vb.valueOf(SendEmailForm.subject)
        val message = vb.valueOf(SendEmailForm.message)
        val senderInfo = user.fullName + { user.email match { case None => "."; case Some(e) => s" at $e."} }
        Email.sendEmail(senderInfo, recipients, subject, message)
        Redirect(routes.Application.sendEmail).flashing(("success" -> "Email Sent!"))
      }
    }
  }
}

case class DistrictInfo(district: District, schools: List[(String, BigDecimal, Set[String])], students: List[(Int, List[(Int, StudentId, BigDecimal)])])