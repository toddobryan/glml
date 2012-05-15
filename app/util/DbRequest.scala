package util

import play.api.mvc.{Request, WrappedRequest}
import models.users.User
import play.api.mvc.Result
import play.api.mvc.BodyParser
import play.api.mvc.BodyParsers.parse
import play.api.mvc.Action
import play.api.mvc.AnyContent

class DbRequest[A](val request: Request[A]) extends WrappedRequest[A](request) {
  val pm = DataStore.getPersistenceManager()
}

object DbAction {
  def apply[A](p: BodyParser[A])(f: DbRequest[A] => Result) = {
    Action(p) (request => {
      val dbReq = new DbRequest[A](request)
      dbReq.pm.beginTransaction()
      val res = f(dbReq)
      dbReq.pm.commitTransactionAndClose()
      res
    })
  }

  def apply(f: DbRequest[AnyContent] => Result) = {
    apply[AnyContent](parse.anyContent)(f)
  }
}