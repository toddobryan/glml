package image

import java.awt.Graphics2D
import math.{abs, min, max}
import java.awt.geom.Rectangle2D

sealed abstract class XAlign
object XAlign {
  object left extends XAlign
  object center extends XAlign
  object right extends XAlign
}

sealed abstract class YAlign
object YAlign {
  object top extends YAlign
  object center extends YAlign
  object bottom extends YAlign
}

class Stack(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) extends Image {
  val backBounds = Stack.translateRect(back.displayBounds, 
		  							   Stack.dx(back.displayBounds, xAlign, 0), 
		  							   Stack.dy(back.displayBounds, yAlign, 0))
  val frontBounds = Stack.translateRect(front.displayBounds,
             							Stack.dx(front.displayBounds, xAlign, dx),
             							Stack.dy(front.displayBounds, yAlign, dy))
  val newX = min(backBounds.getX, frontBounds.getX)
  val newY = min(backBounds.getY, frontBounds.getY)
  val newWidth = max(backBounds.getMaxX, frontBounds.getMaxX) - newX
  val newHeight = max(backBounds.getMaxY, frontBounds.getMaxY) - newY
  val backTopLeft = Point(backBounds.getX - newX, backBounds.getY - newY)
  val frontTopLeft = Point(frontBounds.getX - newX, frontBounds.getY - newY)
  
  def bounds = new Rectangle2D.Double(0, 0, newWidth, newHeight)
  
  def render(g2: Graphics2D) {
    g2.translate(backTopLeft.x, backTopLeft.y)
    back.render(g2)
    g2.translate(frontTopLeft.x - backTopLeft.x, frontTopLeft.y - backTopLeft.y)
    front.render(g2)
    g2.translate(-frontTopLeft.x, -frontTopLeft.y)
  }
}

object Stack {
  def apply(front: Image, back: Image) = new Stack(front, back, XAlign.center, YAlign.center, 0.0, 0.0)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign) = 
      new Stack(front, back, xAlign, yAlign, 0.0, 0.0)
  def apply(front: Image, back: Image, dx: Double, dy: Double) =
      new Stack(front, back, XAlign.center, YAlign.center, dx, dy)
  def apply(front: Image, back: Image, xAlign: XAlign, yAlign: YAlign, dx: Double, dy: Double) =
      new Stack(front, back, xAlign, yAlign, dx, dy)
  
  def translateRect(rect: Rectangle2D, dx: Double, dy: Double): Rectangle2D = {
    new Rectangle2D.Double(rect.getX + dx, rect.getY + dy, rect.getWidth, rect.getHeight)
  }
  
  def dx(rect: Rectangle2D, xAlign: XAlign, xOffset: Double): Double = xOffset + (xAlign match {
    case XAlign.left => 0
    case XAlign.center => -0.5 * (rect.getX + rect.getMaxX)
    case XAlign.right => -rect.getMaxX
  })
  
  def dy(rect: Rectangle2D, yAlign: YAlign, yOffset: Double): Double = yOffset + (yAlign match {
    case YAlign.top => 0
    case YAlign.center => -0.5 * (rect.getY + rect.getMaxY)
    case YAlign.bottom => -rect.getMaxY
  })
}