package image
import java.io.InputStream
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.io.FileInputStream
import java.io.File
import java.awt.image.BufferedImage
import java.awt.geom.Rectangle2D

class Bitmap(val input: InputStream) extends Image {
  override lazy val img: BufferedImage = ImageIO.read(input)

  def bounds = new Rectangle2D.Double(0, 0, img.getWidth, img.getHeight)
  def render(g2: Graphics2D) = g2.drawRenderedImage(img, new AffineTransform())
}

object Bitmap {
  def apply(path: String): Bitmap = new Bitmap(new FileInputStream(path))
  def fromWorkspace(path: String): Bitmap = new Bitmap(getClass.getResourceAsStream(path))
}