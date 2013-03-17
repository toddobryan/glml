package image
import java.awt.BasicStroke

sealed abstract class Join {
  def toBsJoin: Int
}

object Join {
  object Bevel extends Join {
    def toBsJoin = BasicStroke.JOIN_BEVEL
    override def toString = "Join.Bevel"
  }
  object Miter extends Join {
    def toBsJoin = BasicStroke.JOIN_MITER
    override def toString = "Join.Miter"
  }
  object Round extends Join {
    def toBsJoin = BasicStroke.JOIN_ROUND
    override def toString = "Join.Round"
  }
}
