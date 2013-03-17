package forms.fields

import scala.xml._
import forms.widgets._
import forms.validators._
import forms.Form
import util.Helpers.camel2TitleCase
import forms.Binding


abstract class Field[T](val name: String) {
  def validators: List[Validator[T]]

  def required: Boolean = true
  def widget: Widget = new TextInput(required)
  def label: Option[String] = None
  def initialVal: Option[T] = None
  def helpText: Option[String] = None
  def initial: Seq[String] = asStringSeq(initialVal)  
  def spacesSameAsBlank = true
  
  private[this] lazy val _errorMessages: Map[String, String] = {
    Map("required" -> "This field is required.",
        "invalid" -> "Enter a valid value.")
  }
  def errorMessages = _errorMessages
  
  def asStringSeq(value: Option[T]): Seq[String] = value match {
    case Some(t) => List(t.toString)
    case None => Nil
  }
  
  def asValue(s: Seq[String]): Either[ValidationError, T]
  
  def clean(rawData: String): Either[ValidationError, T] = {
    clean(List(rawData))
  }
  
  def clean(rawData: Seq[String]): Either[ValidationError, T] = {
    checkRequired(rawData).fold(
        Left(_), 
        asValue(_).fold(
        	Left(_), validate(_)))
  }
  
  def checkRequired(rawData: Seq[String]): Either[ValidationError, Seq[String]] = {
    rawData match {
      case Seq() => if (this.required) Left(ValidationError(errorMessages("required")))
      	  else Right(rawData)
      case Seq(strs@_*) => {
        if (strs.exists(s => (if (spacesSameAsBlank) s.trim else s) != "")) {
          Right(rawData)
        } else if (required) {
          Left(ValidationError(errorMessages("required")))
        } else {
          Right(Nil)
        }
      } 
    }
  }
  
  def htmlName(form: Form) = form.addPrefix(name)
  def htmlInitialName(form: Form) = form.addInitialPrefix(name)
  def htmlInitialId(form: Form) = form.addInitialPrefix(autoId(form).getOrElse(""))
  
  def asWidget(bound: Binding, widget: Widget = widget, attrs: MetaData = Null, onlyInitial: Boolean = false): NodeSeq = {
    val idAttr = if (autoId(bound.form).isDefined && attrs.get("id").isEmpty && widget.attrs.get("id").isEmpty) {
      new UnprefixedAttribute("id", Text(if (!onlyInitial) autoId(bound.form).get else htmlInitialId(bound.form)), Null)
    } else {
      Null
    }
    widget.render(if (!onlyInitial) htmlName(bound.form) else htmlInitialName(bound.form), bound.asStringSeq(this), attrs.append(idAttr))
  }
  
  def labelTag(form: Form, contents: Option[String] = None, attrs: MetaData = Null): NodeSeq = {
    val text = contents.getOrElse(label.getOrElse(camel2TitleCase(name)))
	val id = widget.attrs.get("id") match {
	  case Some(theId) => Some(theId)
	  case None => autoId(form).map(Text(_))
	}
	if (id.isDefined) {
	  <label class="control-label">{ text }</label> % new UnprefixedAttribute("for", id, attrs)
	} else {
	  Text(text)
	}    
  }

  def autoId(form: Form): Option[String] = {
    val htmlName = form.addPrefix(name)
    form.autoId.map(id => {
      if (id.contains("%s")) id.format(htmlName)
      else htmlName
    })
  }

  
  def validate(value: T): Either[ValidationError, T] = {
    val errors = ValidationError(this.validators.flatMap(_.apply(value)))
    if (errors.isEmpty) Right(value) else Left(errors)
  } 
  
  def boundData(data: Seq[String], initial: Seq[String]): Seq[String] = data 
  
  def widgetAttrs(widget: Widget): MetaData = Null
}
