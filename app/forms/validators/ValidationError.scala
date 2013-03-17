package forms.validators

import scala.xml.NodeSeq

// TODO: change to list of NodeSeq so can include tags
class ValidationError(val messages: List[String]) extends Seq[String] {
  def apply(idx: Int) = messages(idx)
  def iterator = messages.iterator
  def length = messages.length
  
  def asHtml: NodeSeq = <div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">×</button>
  	{ messages.flatMap(msg => <p>{ msg }</p>) }</div> 
}

object ValidationError {
  def apply(message: String): ValidationError = ValidationError(List(message))
  
  def apply(messages: List[String]): ValidationError = new ValidationError(messages)
}