import scala.language.implicitConversions

package object image {
  import AngleUnit._
  
  implicit def doubleToAngleBuilder(d: Double) = new AngleBuilder(d)
}