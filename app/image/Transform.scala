package image

import java.awt.image.BufferedImage
import java.awt.geom._
import java.awt.image._
import java.awt.{Graphics2D, Paint, RenderingHints}
import java.awt.Shape

class Transform(image: Image, transform: AffineTransform) extends Image {
  def render(g2: Graphics2D) = {
    val inverse = new AffineTransform(transform)
    inverse.invert
    g2.transform(transform)
    image.render(g2)
    g2.transform(inverse)
  }
  
  def bounds: Shape = transform.createTransformedShape(image.bounds)
}
