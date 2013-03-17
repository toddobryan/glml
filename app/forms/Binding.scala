package forms

import fields.Field
import validators.ValidationError
import scala.xml.Elem

object Binding {
  def apply(form: Form): InitialBinding = {
    new InitialBinding(form)
  }
  
  def apply(form: Form, rawData: Map[String, Seq[String]]): Binding = {
    val valuesOrErrors: List[(String, Either[ValidationError, Any])] = form.fields.map(f => (f.name, f.clean(rawData.getOrElse(f.name, Nil))))
    val (values, errors) = valuesOrErrors.partition(_._2.isRight)
    val fieldErrors: Map[String, ValidationError] = Map(errors.map(nmEr => (nmEr._1, nmEr._2.left.get)): _*)
    val cleanedData: Map[String, Any] = Map(values.map(nmVal => (nmVal._1, nmVal._2.right.get)): _*)
    val validSoFar: ValidBinding = new ValidBinding(form, rawData, cleanedData)
    val formErrors: ValidationError = if (fieldErrors.isEmpty) form.validate(new ValidBinding(form, rawData, cleanedData)) else ValidationError(Nil)
    if (fieldErrors.isEmpty && formErrors.isEmpty) validSoFar else new InvalidBinding(form, rawData, fieldErrors, formErrors)
  }
  
  def apply(form: Form, rawData: Map[String, String])(implicit d: DummyImplicit): Binding = {
    apply(form, rawData.map {
      case (name, value) => (name, List(value))
    })
  }
  
  def apply(form: Form, request: play.api.mvc.Request[_]): Binding = {
    apply(form, 
      (request.body match {
        case body: play.api.mvc.AnyContent if body.asFormUrlEncoded.isDefined => body.asFormUrlEncoded.get
        case body: play.api.mvc.AnyContent if body.asMultipartFormData.isDefined => body.asMultipartFormData.get.asFormUrlEncoded
        //case body: play.api.mvc.AnyContent if body.asJson.isDefined => FormUtils.fromJson(js = body.asJson.get).mapValues(Seq(_))
        case body: Map[_, _] => body.asInstanceOf[Map[String, Seq[String]]]
        case body: play.api.mvc.MultipartFormData[_] => body.asFormUrlEncoded
        //case body: play.api.libs.json.JsValue => FormUtils.fromJson(js = body).mapValues(Seq(_))
        case _ => Map.empty[String, Seq[String]]
      }) ++ request.queryString)
  }
}

abstract class Binding(val form: Form, val rawData: Map[String, Seq[String]]) {
  def formErrors: ValidationError = new ValidationError(Nil)
  def fieldErrors: Map[String, ValidationError] = Map()
  def fieldErrors(field: Field[_]): Option[ValidationError] = fieldErrors.get(field.name)
  def hasErrors: Boolean = !(formErrors.isEmpty && fieldErrors.isEmpty)
  def asHtml: Elem = form.asHtml(this)
  
  def asStringSeq(field: Field[_]): Seq[String] = {
    rawData.getOrElse(field.name, Nil)
  }
}

class InitialBinding(form: Form) extends Binding(form, Map()) {
  override def asStringSeq(field: Field[_]): Seq[String] = {
    field.initial
  }
}

class InvalidBinding(form: Form, rawData: Map[String, Seq[String]], _fieldErrors: Map[String, ValidationError], _formErrors: ValidationError)
	extends Binding(form, rawData) {
  override def formErrors = _formErrors
  override def fieldErrors = _fieldErrors
}

class ValidBinding(form: Form, rawData: Map[String, Seq[String]], val cleanedData: Map[String, Any])
	extends Binding(form, rawData) {
  override def hasErrors: Boolean = false
  
  def valueOf[T](field: Field[T]): T = {
    cleanedData(field.name).asInstanceOf[T]
  }
}

