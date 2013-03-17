package image

import java.awt.BasicStroke

sealed abstract class Cap {
  def toBsCap: Int
}

object Cap {
  object None extends Cap {
    def toBsCap = BasicStroke.CAP_BUTT
    override def toString = "Cap.None"
  }
  object Round extends Cap {
    def toBsCap = BasicStroke.CAP_ROUND
    override def toString = "Cap.Round"
  }
  object Square extends Cap {
    def toBsCap = BasicStroke.CAP_SQUARE
    override def toString = "Cap.Square"
  }
}