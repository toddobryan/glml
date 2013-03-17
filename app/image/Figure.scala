package image
import java.awt.{Graphics2D, Paint, RenderingHints}
import java.awt.Shape
import java.awt.geom.Rectangle2D

abstract class Figure extends Image {
  val awtShape: Shape
  def bounds: Shape = awtShape
}

abstract class FigureFilled(val paint: Paint) extends Figure {
  def render(g2: Graphics2D) = {
    g2.setPaint(paint)
    g2.fill(awtShape)
  }
}

abstract class FigureOutlined(val pen: Pen) extends Figure {
  def render(g2: Graphics2D) = {
    g2.setPaint(pen.paint)
    g2.setStroke(pen.asStroke)
    g2.draw(awtShape)
  }
  override def displayBounds: Rectangle2D = {
    val myPen = if (pen.width == 0.0) pen.copy(width=1.0) else pen
    myPen.asStroke.createStrokedShape(awtShape).getBounds2D()
  }
}


