package image

import java.awt.Paint

private[image] object Ellipse {
  def shape(width: Double, height: Double) = new java.awt.geom.Ellipse2D.Double(0, 0, width, height)
}

private[image] class EllipseFilled(paint: Paint, width: Double, height: Double) extends FigureFilled(paint) {
  val awtShape = Ellipse.shape(width, height)
}

object EllipseFilled {
  def apply(paint: Paint, width: Double, height: Double): EllipseFilled = {
    new EllipseFilled(paint, width, height)
  }
}

private[image] class EllipseOutlined(pen: Pen, width: Double, height: Double) extends FigureOutlined(pen) {
  val awtShape = Ellipse.shape(width, height)
}

object EllipseOutlined {
  def apply(pen: Pen, width: Double, height: Double): EllipseOutlined = {
    new EllipseOutlined(pen, width, height)
  }
  
  def apply(color: java.awt.Color, width: Double, height: Double): EllipseOutlined = {
    new EllipseOutlined(Pen(color), width, height)
  }
}

private[image] class CircleFilled(paint: Paint, radius: Double) extends FigureFilled(paint) {
  val awtShape = Ellipse.shape(2 * radius, 2 * radius)
}

object CircleFilled {
  def apply(paint: Paint, radius: Double) = new CircleFilled(paint, radius)
}

private[image] class CircleOutlined(pen: Pen, radius: Double) extends FigureOutlined(pen) {
  val awtShape = Ellipse.shape(2 * radius, 2 * radius)
}

object CircleOutlined {
  def apply(pen: Pen, radius: Double) = new CircleOutlined(pen, radius)
  def apply(color: java.awt.Color, radius: Double) = new CircleOutlined(Pen(color), radius)
}

private[image] object Arc {
  import java.awt.geom.Arc2D
  
  val OPEN = Arc2D.OPEN
  val CHORD = Arc2D.CHORD
  val PIE = Arc2D.PIE
  
  def shape(width: Double, height: Double, start: Angle, extent: Angle, kind: Int): java.awt.Shape = {
    val startDeg = start.toDegrees.amount
    val extentDeg = extent.toDegrees.amount
    val bounds = new Arc2D.Double(0, 0, width, height, startDeg, extentDeg, kind).getBounds2D()
    new Arc2D.Double(-bounds.getMinX, -bounds.getMinY, width, height, startDeg, extentDeg, kind)
  }
}

private[image] class CircularArc(pen: Pen, radius: Double, start: Angle, extent: Angle)
    extends FigureOutlined(pen) {
 val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.OPEN)
}

object CircularArc {
  def apply(pen: Pen, radius: Double, start: Angle, extent: Angle) = {
    new CircularArc(pen, radius, start, extent)
  }
  def apply(color: java.awt.Color, radius: Double, start: Angle, extent: Angle) = {
    new CircularArc(Pen(color), radius, start, extent)
  }
}

private[image] class CircularSegment(paint: Paint, radius: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.CHORD)
}

object CircularSegment {
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle) = {
    new CircularSegment(paint, radius, start, extent)
  }
}

private[image] class CircularWedge(paint: Paint, radius: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(2 * radius, 2 * radius, start, extent, Arc.PIE)
}

object CircularWedge {
  def apply(paint: Paint, radius: Double, start: Angle, extent: Angle) = {
    new CircularWedge(paint, radius, start, extent)
  }
}

private[image] class EllipticalArc(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle)
    extends FigureOutlined(pen) {
 val awtShape = Arc.shape(width, height, start, extent, Arc.OPEN)
}

object EllipticalArc {
  def apply(pen: Pen, width: Double, height: Double, start: Angle, extent: Angle) = {
    new EllipticalArc(pen, width, height, start, extent)
  }
  def apply(color: java.awt.Color, width: Double, height: Double, start: Angle, extent: Angle) = {
    new EllipticalArc(Pen(color), width, height, start, extent)
  }
}

private[image] class EllipticalSegment(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(width, height, start, extent, Arc.CHORD)
}

object EllipticalSegment {
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle) = {
    new EllipticalSegment(paint, width, height, start, extent)
  }
}

private[image] class EllipticalWedge(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle)
    extends FigureFilled(paint) {
  val awtShape = Arc.shape(width, height, start, extent, Arc.PIE)
}

object EllipticalWedge {
  def apply(paint: Paint, width: Double, height: Double, start: Angle, extent: Angle) = {
    new EllipticalWedge(paint, width, height, start, extent)
  }
}

