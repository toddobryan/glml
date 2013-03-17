/* package image

import java.awt.Font
import java.awt.Graphics2D
import java.awt.font.TextAttribute
import java.text.AttributedString

class Text(string: String, color: Pen, size: Int) extends Image{
  val font = new Font("Plain Text", java.awt.Font.PLAIN, size)
  
  val as = new AttributedString(string)
  as.addAttribute(TextAttribute.FONT, font)
  as.addAttribute(TextAttribute.FOREGROUND, color)

}
*/
