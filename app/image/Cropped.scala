package image
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform
import java.awt.Shape

class Cropped(val image: Image, val x: Double, val y: Double, val w: Double, val h: Double) extends Image {
  if (x < 0 || x > image.width) {
    throw new Exception("Illegal x value %d is outside the image, which is %d pixels wide.".format(x, image.width))
  } else if (y < 0 || y > image.height) {
    throw new Exception("Illegal y value %d is outside the image, which is %d pixels tall.".format(y, image.height))
  } else if (w < 0 || x + w > image.width) {
    throw new Exception("Illegal width %d (must be at least zero and %d + width must be less than %d).".format(w, x, image.width))
  } else if (h < 0 || y + h > image.height) {
    throw new Exception("Illegal height %d (must be at least zero and %d + height must be less than %d).".format(h, y, image.height))
  }
  def render(g2: Graphics2D) = {
    val origClip = g2.getClip
    g2.setClip(new Rectangle2D.Double(0, 0, w, h))
    g2.drawRenderedImage(image.displayedImg, AffineTransform.getTranslateInstance(-x, -y))
    g2.setClip(origClip)
  }
  def bounds: Shape = new Rectangle2D.Double(0, 0, w, h)
}