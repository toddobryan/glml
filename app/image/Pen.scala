package image

import java.awt.{BasicStroke, Paint, Stroke}

case class Pen(paint: Paint, width: Double, cap: Cap, join: Join, dash: Option[Dash]) {
  def asStroke: Stroke = {
    val (dashPatt, dashPhase) = dash.map(d => (d.patt, d.off)).getOrElse((null, 0.0f))
    new BasicStroke(width.toFloat, cap.toBsCap, join.toBsJoin, 10.0f, dashPatt, dashPhase)
  }
}

object Pen {
  def apply(color: java.awt.Color): Pen = Pen(color, 0.0)
  def apply(color: java.awt.Color, width: Double): Pen = Pen(color, width, Cap.None, Join.Bevel, None)
}