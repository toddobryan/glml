package forms.fields
import forms.widgets.PasswordInput

class PasswordField(name: String) extends TextField(name) {
  override def widget = new PasswordInput(this.required)
}