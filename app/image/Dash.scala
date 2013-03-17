package image

class Dash(pattern: List[Double], offset: Double) {
  val patt: Array[Float] = pattern.map(_.toFloat).toArray
  val off: Float = offset.toFloat
}

object Dash {
  def apply(lengths: Double*) = new Dash(lengths.toList, 0)
  
  val short = Dash(3, 2)
  val long = Dash(5, 4)
  val dot = Dash(1, 2)
  val dotDash = Dash(1, 3, 4, 3)
}
