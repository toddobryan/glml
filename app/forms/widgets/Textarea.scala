package forms.widgets

import scala.xml._

class Textarea(
    required: Boolean,
    attrMap: MetaData = Null) extends 
    	Widget(required, 
    	    new UnprefixedAttribute("cols", Text("40"), 
    	    	new UnprefixedAttribute("rows", Text("10"), Null).append(attrMap))) {

  def render(name: String, value: Seq[String], attrList: MetaData = Null): NodeSeq = {
    <textarea name={ name }>{ if (value.isEmpty) "" else value(0) }</textarea> % attrs.append(attrList)
  }

}