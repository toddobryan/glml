package forms.widgets

import scala.xml._

//TODO: what about forms where a list of checkboxes refers to the same name
class CheckboxInput(
    attrs: MetaData = Null,
    val checkFun: (Seq[String] => Boolean) = CheckboxInput.defaultCheckFun(_))
  extends Widget(false, attrs) {

  def render(name: String, value: Seq[String], attrList: MetaData = Null): NodeSeq = {
    val checked = if (checkFun(value)) new UnprefixedAttribute("checked", Text("checked"), Null)
    			  else Null
    val valueAttr = if (CheckboxInput.worthMentioning(value)) new UnprefixedAttribute("value", Text(value(0)), Null)
                    else Null
    <input type="checkbox" name={ name } /> % attrs.append(attrList).append(checked).append(valueAttr)
  }
}

object CheckboxInput {
  def defaultCheckFun(value: Seq[String]): Boolean = {
    value match {
      case Seq(str) => str != "" && str != "false"
      case _ => false
    }
  }
  
  def worthMentioning(value: Seq[String]): Boolean = {
    value match {
      case Seq(str) => str != "" && str != "true" && str != "false"
      case _ => false
    }
  }
}