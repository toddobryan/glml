package image

case class Point(x: Double, y: Double) {
  def translate(dx: Double, dy: Double) = Point(x + dx, y + dy)
}

case class PointPolar(r: Double, theta: Angle) {
  def toCartesian: Point = Point(r * theta.cos, r * theta.sin)
}
