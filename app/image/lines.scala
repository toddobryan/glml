package image

import math.{min, Pi}

import java.awt.Paint

private[image] object Poly {
  def topLeft(pts: List[Point]): Point = pts match {
    case Nil => throw new Exception("can't create Polygon with no vertices")
    case p :: Nil => p
    case p :: rest => {
      val tlOfRest = topLeft(rest)
      Point(min(p.x, tlOfRest.x), min(p.y, tlOfRest.y))
    }
  }

  def shape(vertices: List[Point], close: Boolean) = {
    val tl = Poly.topLeft(vertices)
    val path = new java.awt.geom.Path2D.Double
    val transVs = vertices.map(p => p.translate(-tl.x, -tl.y))
    val start = if (close) transVs.last else transVs.head
    path.moveTo(start.x, start.y)
    for (v <- (if (close) transVs else transVs.tail)) {
      path.lineTo(v.x, v.y)
    }
    path    
  }
  
  def regular(sideLength: Double, numSides: Int): List[Point] = {
    if (numSides < 3) throw new Exception("a polygon must have at least three sides")
    val radius = sideLength / (2.0 * (Pi / numSides).radians.sin)
    val offsetAngle = {
      if (numSides % 2 == 1) (-Pi / 2).radians
      else if (numSides % 4 == 0) (-Pi / numSides).radians
      else 0.radians
    }
    val polarVertices = (0 to (numSides - 1)).toList.map(n => PointPolar(radius, (n * 2.0 * Pi / numSides).radians + offsetAngle))
    polarVertices.map(_.toCartesian)
  }
}

private[image] class Polygon(paint: Paint, vertices: List[Point]) extends FigureFilled(paint) {
  val awtShape = Poly.shape(vertices, true)
  override def toString = "Polygon(%s, %s)".format(paint, vertices.mkString(", "))
}

object Polygon {  
  def apply(
      paint: Paint, 
      vertex1: Point, vertex2: Point, vertex3: Point, 
      restOfVertices: Point*): Image = {
    new Polygon(paint, vertex1 :: vertex2 :: vertex3 :: restOfVertices.toList) 
  }
}

private[image] class Polyline(pen: Pen, vertices: List[Point]) extends FigureOutlined(pen) {
  val awtShape = Poly.shape(vertices, false)
  override def toString = "Polyline(%s, %s)".format(pen, vertices.mkString(", "))
}

object Polyline {
  def apply(pen: Pen, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new Polyline(pen, vertex1 :: vertex2 :: restOfVertices.toList)
  }
  def apply(color: java.awt.Color, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    Polyline(Pen(color), vertex1, vertex2, restOfVertices: _*)
  }
}

object RegularPolygonFilled {
  def apply(paint: Paint, sideLength: Double, numSides: Int): Image = {
    new Polygon(paint, Poly.regular(sideLength, numSides))
  }
}

object RegularPolygonOutlined {
  def apply(pen: Pen, sideLength: Double, numSides: Int): Image = {
    val vts = Poly.regular(sideLength, numSides)
    new Polyline(pen, vts.last :: vts)
  }
  def apply(color: java.awt.Color, sideLength: Double, numSides: Int): Image = {
    RegularPolygonOutlined(Pen(color), sideLength, numSides)
  }
}

private[image] object Rectangle {
  def shape(width: Double, height: Double) = new java.awt.geom.Rectangle2D.Double(0, 0, width, height)
}

private[image] class RectangleFilled(paint: Paint, width: Double, height: Double) extends FigureFilled(paint) {
  val awtShape = Rectangle.shape(width, height)
}

object RectangleFilled {
  def apply(paint: Paint, width: Double, height: Double): RectangleFilled =
    new RectangleFilled(paint, width, height)
}

private[image] class RectangleOutlined(pen: Pen, width: Double, height: Double) extends FigureOutlined(pen) {
  val awtShape = Rectangle.shape(width, height)
}

object RectangleOutlined {
  def apply(pen: Pen, width: Double, height: Double): RectangleOutlined = {
    new RectangleOutlined(pen, width, height)
  }
  
  def apply(color: java.awt.Color, width: Double, height: Double): RectangleOutlined = {
    new RectangleOutlined(Pen(color), width, height)
  }
}

private[image] object Square {
  def shape(side: Double) = new java.awt.geom.Rectangle2D.Double(0, 0, side, side)
}

private[image] class SquareFilled(paint: Paint, side: Double) extends FigureFilled(paint) {
  val awtShape = Square.shape(side)
}

object SquareFilled {
  def apply(paint: Paint, side: Double): SquareFilled = 
    new SquareFilled(paint, side)
}

private[image] class SquareOutlined(pen: Pen, side: Double) extends FigureOutlined(pen) {
  val awtShape = Square.shape(side)
}

object SquareOutlined {
  def apply(pen: Pen, side: Double): SquareOutlined =
    new SquareOutlined(pen, side)
  
  def apply(color: java.awt.Color, side: Double): SquareOutlined =
    new SquareOutlined(Pen(color), side)
}