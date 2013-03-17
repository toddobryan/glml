package forms

import scala.collection.immutable.ListMap
import scala.xml._
import forms.fields._
import forms.widgets.Widget
import util.Helpers.camel2TitleCase
import forms.validators.ValidationError
import play.api.mvc.Request

abstract class Form {
  // TODO: check that fields have unique names?
  def fields: List[Field[_]]
  def validate(data: ValidBinding): ValidationError = new ValidationError(Nil)
  
  def method = "post"
  def autoId: Option[String] = Some("id_%s")
  def prefix: Option[String] = None
  def labelSuffix: String = ":"
  
  def asHtml(bound: Binding): Elem = {
    <form method={ method } class="form-horizontal well"><fieldset>
    { if (bound.formErrors.isEmpty) NodeSeq.Empty else { bound.formErrors.asHtml } }  
    {fields.flatMap(f => {
      val name = f.name
      val label = f.label.getOrElse(camel2TitleCase(f.name))
      val labelName = if (label == "") "" else {
        if (":?.!".contains(label.substring(label.length - 1, label.length))) label
        else label + labelSuffix
      }
      val labelPart = 
        if (labelName != "") f.labelTag(this, Some(labelName)) ++ Text(" ")
        else NodeSeq.Empty
      val errorList = bound.fieldErrors.get(name).map(_.asHtml)
      <div class="control-group">
        { labelPart }
        <div class="controls">{ f.asWidget(bound) }</div>
        { if (bound.hasErrors) { errorList.getOrElse(NodeSeq.Empty) } 
          else NodeSeq.Empty }
      </div>
    }).toList
    }
    <div class="form-actions">
    	<button type="submit" class="btn btn-primary">Submit</button>
    	<button type="reset" class="btn">Cancel</button>
    </div></fieldset>
    </form> 
  }
    
  def addPrefix(fieldName: String): String = {
    prefix.map(p => "%s-%s".format(p, fieldName)).getOrElse(fieldName)
  }
  
  def addInitialPrefix(fieldName: String): String = {
    "initial-%s".format(addPrefix(fieldName))
  }
}



