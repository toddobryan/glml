package forms.widgets

import scala.xml._

class TextInput(
    required : Boolean,
    attrs: MetaData = Null, _inputType: String = "text") extends Input(required, attrs) {
  
  def inputType: String = _inputType
}
