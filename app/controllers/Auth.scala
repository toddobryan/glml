package controllers

import play.api.mvc.Controller
import forms.{Form, ValidBinding}
import forms.fields.{TextField, PasswordField}
import models.auth.VisitAction
import forms.validators.ValidationError
import scalajdo.DataStore
import models.auth.Method
import models.auth.User
import forms.{Binding, InvalidBinding, ValidBinding}


object LoginForm extends Form {
  val username = new TextField("username")
  val password = new PasswordField("password")
  
  def fields = List(username, password)
  
  override def validate(vb: ValidBinding): ValidationError = {
    User.authenticate(vb.valueOf(username), vb.valueOf(password)) match {
      case None => ValidationError("Incorrect username or password.")
      case Some(user) => ValidationError(Nil)
    }
  }
}

object Auth extends Controller {
  def login() = VisitAction { implicit req => 
    if (req.method == Method.GET) {
      Ok(views.html.auth.login(Binding(LoginForm)))
    } else {
      Binding(LoginForm, req) match {
        case ib: InvalidBinding => Ok(views.html.auth.login(ib))
        case vb: ValidBinding => {
          // set the session user
          req.visit.user = User.getByUsername(vb.valueOf(LoginForm.username))
          val redirectUrl: Option[String] = req.visit.redirectUrl
          req.visit.redirectUrl = None
          DataStore.pm.makePersistent(req.visit)
          redirectUrl.map(Redirect(_)).getOrElse(Redirect(routes.Application.index())).flashing("success" -> "You have successfully logged in.")
        }
      }
    }
  }
  
  def logout() = VisitAction { implicit req =>
    DataStore.pm.deletePersistent(req.visit)
    Redirect(routes.Application.index()).flashing("success" -> "You have successfully logged out.") 
  }
}
