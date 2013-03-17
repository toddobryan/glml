package image

import math.{abs, round, Pi}

object AngleUnit extends Enumeration {
  type AngleUnit = Value
  val degrees, radians = Value
}

class Angle(val amount: Double, val units: AngleUnit.AngleUnit) {
  def toRadians: Angle = units match {
    case AngleUnit.radians => this
    case AngleUnit.degrees => Angle(amount * Pi / 180.0, AngleUnit.radians)
  }
  
  def toDegrees: Angle = units match {
    case AngleUnit.degrees => this
    case AngleUnit.radians => Angle(amount * 180.0 / Pi, AngleUnit.degrees)
  }
  
  /**
   * produces a reference angle between -Pi and Pi (or -180 and 180)that is 
   * co-terminal with this angle and in the same units
   */
  lazy val refAngle: Angle = units match {
    case AngleUnit.degrees => {
      if (amount >= -180.0 && amount <= 180.0) this
      else {
        val revols = round(abs(amount) / 360.0)
        if (amount < 0) Angle(amount + 360.0 * revols, AngleUnit.degrees)
        else Angle(amount - 360.0 * revols, AngleUnit.degrees)
      }
    }
    case AngleUnit.radians => {
      if (amount >= -Pi && amount <= Pi) this
      else {
        val revols = round(abs(amount) / (2 * Pi))
        if (amount < 0) Angle(amount + (2 * Pi * revols), AngleUnit.radians)
        else Angle(amount - (2 * Pi * revols), AngleUnit.radians)
      }
    }
  }
  
  def +(that: Angle) = {
    if (this.units == that.units) Angle(this.amount + that.amount, this.units)
    else this.units match {
      case AngleUnit.radians => Angle(this.amount + that.toRadians.amount, AngleUnit.radians)
      case AngleUnit.degrees => Angle(this.amount + that.toDegrees.amount, AngleUnit.degrees)
    }
  }
  
  def sin: Double = {
    import Angle.~=
    val refRads: Double = refAngle.toRadians.amount
    if (~=(refRads, 0.0) || ~=(refRads, Pi) || ~=(refRads, -Pi)) 0.0
    else if (~=(refRads, Pi / 6.0) || ~=(refRads, 5.0 * Pi / 6.0)) 0.5
    else if (~=(refRads, -Pi / 6.0) || ~=(refRads, -5.0 * Pi / 6.0)) -0.5
    else if (~=(refRads, -Pi / 2.0)) -1.0
    else if (~=(refRads, Pi / 2.0)) 1.0
    else math.sin(refRads)
  }
  
  def cos: Double = {
    import Angle.~=
    val refRads: Double = refAngle.toRadians.amount
    if (~=(refRads, 0.0)) 1.0
    else if (~=(refRads, Pi) || ~=(refRads, -Pi)) -1.0
    else if (~=(refRads, Pi / 3.0) || ~=(refRads, -Pi / 3.0)) 0.5
    else if (~=(refRads, 2.0 * Pi / 3.0) || ~=(refRads, -2.0 * Pi / 3.0)) -0.5
    else if (~=(refRads, -Pi / 2.0) || ~=(refRads, Pi / 2.0)) 0.0
    else math.cos(refRads)
  }
  
  def tan: Double = {
    import Angle.~=
    val refRads: Double = refAngle.toRadians.amount
    if (~=(refRads, 0.0) || ~=(refRads, Pi) || ~=(refRads, -Pi)) 0.0
    else if (~=(refRads, Pi / 4.0) || ~=(refRads, -3.0 * Pi / 4.0)) 1.0
    else if (~=(refRads, 3.0 * Pi / 4.0) || ~=(refRads, -Pi / 4.0)) -1.0
    else if (~=(refRads, -Pi / 2.0) || ~=(refRads, Pi / 2.0)) Double.NaN
    else math.tan(refRads)    
  }
  
  override def toString: String = {
    "%f %s".format(amount, units)
  }
  
  def canEqual(other: Any): Boolean =
    other.isInstanceOf[Angle]

  override def equals(other: Any): Boolean = other match {
    case that: Angle => {
      (that canEqual this) &&
      (if (units == that.units) amount == that.amount
      else (toRadians.amount == that.toRadians.amount))
    }
    case _ => false
  }
}

object Angle {
  def apply(amount: Double, units: AngleUnit.AngleUnit): Angle = new Angle(amount, units)
  
  def ~=(d1: Double, d2: Double): Boolean = abs(d1 - d2) < 0.000000001
  
  type AngleUnit = AngleUnit.AngleUnit
  val degrees = AngleUnit.degrees
  val radians = AngleUnit.radians
}

class AngleBuilder(amt: Double) {
  def degrees: Angle = Angle(amt, AngleUnit.degrees)
  def radians: Angle = Angle(amt, AngleUnit.radians)
}

