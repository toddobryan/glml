package forms.fields
import forms.validators.ValidationError

abstract class BooleanField(name: String) extends Field[Boolean](name) {
  override def required = false
  
  override def asStringSeq(value: Option[Boolean]) = value match {
    case Some(true) => List("true")
    case _ => Nil
  }
  
  def asValue(s: Seq[String]): Either[ValidationError, Boolean] = s match {
    case Seq("true") => Right(true)
    case Seq() => Right(false)
    case _ => Left(ValidationError("A boolean value may be either true or absent."))
  }
}