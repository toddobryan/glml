package controllers

import play.api.mvc.{Controller, AnyContent}
import org.dupontmanual.forms
import forms.{Form, ValidBinding}
import forms.fields.{TextField, PasswordField}
import models.auth.{VisitAction, VisitRequest, Authenticated, AuthVisitRequest}
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

class ChangePasswordForm(val user: User) extends Form {
  val oldPw = new PasswordField("Current Password")
  val newPw = new PasswordField("New Password")
  val newPwConfirm = new PasswordField("Confirm New Password")
    
  val fields = List(oldPw, newPw, newPwConfirm)
  
  override def validate(vb: ValidBinding): ValidationError = {
    if(!user.passwordChecks(vb.valueOf(oldPw))) ValidationError("Incorrect password.")
    else if(vb.valueOf(newPw) != vb.valueOf(newPwConfirm)) ValidationError("Password do not match.")
    else ValidationError(Nil)
  }
}
  
object ChangeEmailForm extends Form {
  val newE = new TextField("New Email")
  val newEConfirm = new TextField("Confirm New Email")
  
  val fields = List(newE, newEConfirm)
  
  override def validate(vb: ValidBinding): ValidationError = {
    if(vb.valueOf(newE) == vb.valueOf(newEConfirm)) ValidationError(Nil)
    else ValidationError("Emails did not match")
  }
}

object Auth extends Controller {
  def login() = VisitAction { implicit req =>
    Ok(views.html.auth.login(Binding(LoginForm)))
  }
  
  def loginP() = VisitAction { implicit req => 
    Binding(LoginForm, req) match {
      case ib: InvalidBinding => Ok(views.html.auth.login(ib))
      case vb: ValidBinding => {
        // set the session user
        req.visit.user = User.getByUsername(vb.valueOf(LoginForm.username))
        val redirectUrl: Option[String] = req.visit.redirectUrl
        req.visit.redirectUrl = None
        DataStore.pm.makePersistent(req.visit)
        redirectUrl.map(Redirect(_)).getOrElse(Redirect(routes.Application.index)).flashing("success" -> "You have successfully logged in.")
      }
    }
  }
  
  def logout() = VisitAction { implicit req =>
    DataStore.pm.deletePersistent(req.visit)
    Redirect(routes.Application.index()).flashing("success" -> "You have successfully logged out.") 
  }
  
  def changeSettings = Authenticated { implicit authReq => 
    implicit val req = authReq.vrequest
    implicit val user = authReq.user
    Ok(views.html.auth.changeSettings(Binding(new ChangePasswordForm(user)), Binding(ChangeEmailForm)))
  }
  
  def changePassword =  Authenticated { implicit authReq => 
    implicit val (req, user) = authReq match { case AuthVisitRequest(r, u) => (r, u); case _ => (null, null) }
    Binding(new ChangePasswordForm(user), req) match {
      case ib: InvalidBinding => Ok(views.html.auth.changeSettings(ib, Binding(ChangeEmailForm)))
      case vb: ValidBinding => {
        user.setPassword(vb.valueOf((new ChangePasswordForm(user)).newPw))
        Redirect(routes.Auth.changeSettings).flashing("success" -> "Password changed successfully.")
      }
    }
  }
    
  def changeEmail = Authenticated { implicit authReq => 
    implicit val (req, user) = authReq match { case AuthVisitRequest(r, u) => (r, u); case _ => (null, null) }
    Binding(ChangeEmailForm, req) match {
      case ib: InvalidBinding => Ok(views.html.auth.changeSettings(Binding(new ChangePasswordForm(user)), ib))
      case vb: ValidBinding => {
        user.email_=(vb.valueOf(ChangeEmailForm.newE))
        Redirect(routes.Auth.changeSettings).flashing("success" -> "Email changed successfully.")
      }
    }
  }
}
