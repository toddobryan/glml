package controllers

import play.api._
import play.api.mvc._
import models.auth.VisitAction

import models._

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

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
  
  // I don't think this will work yet
  def indexWithDate(year: Int, month: Int, day: Int) = VisitAction { implicit req =>
    try {
      index(Some(new LocalDate(year, month, day))).asInstanceOf[PlainResult]
    } catch {
      case e: IllegalFieldValueException => Redirect(routes.Application.index()).flashing("error" -> "That's not a valid date!")
    }
  }
  
}

case class DistrictInfo(district: District, schools: List[(String, BigDecimal, Set[String])], students: List[(Int, List[(Int, StudentId, BigDecimal)])])