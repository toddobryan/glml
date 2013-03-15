package controllers

import play.api._
import play.api.mvc._
import util.{DataStore, ScalaPersistenceManager}
import util.DbAction

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Random test string..."))
  }
  
}