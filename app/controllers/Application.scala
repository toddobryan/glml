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
    val workingYear = req.visit.workingYear
      
    Ok(views.html.index(null))
  }
  
  def indexWithDate(year: Int, month: Int, day: Int) = {
    try {
      index(Some(new LocalDate(year, month, day)))
    } catch {
      case e: IllegalFieldValueException => TODO //TODO
      //Redirect(controllers.Application.index()).flashing("error" -> "That's not a valid date!")
    }
  }
  
}

case class DistrictInfo(district: District, schools: List[School], students: List[Student])