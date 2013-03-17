package forms.fields

import forms.widgets._
import forms.validators._

// TODO: use type="number" from HTML5
trait BaseNumericField[T] {
  val minValue: Option[T] = None
  val maxValue: Option[T] = None
}

class NumericField[T](name: String)(implicit n: Numeric[T], man: Manifest[T]) 
    extends Field[T](name) with BaseNumericField[T] {
  def asValue(strs: Seq[String]): Either[ValidationError, T] = {
    val (toT, errorMsg) = NumericField.conversionFunction[T]
    strs match {
      case Seq(s) => try {
        Right(toT(s.trim))
      } catch {
        case e: NumberFormatException => Left(ValidationError(errorMsg(s)))
      }
      case _ => Left(ValidationError("Expected a single value, got none or many."))
    }
  }
  
  def validators = NumericField.minAndMaxValidators(minValue, maxValue)
}

class NumericFieldOptional[T](name: String)(implicit n: Numeric[T], man: Manifest[T])
    extends Field[Option[T]](name) with BaseNumericField[T] {
  override def required = false
  
  def asValue(strs: Seq[String]): Either[ValidationError, Option[T]] = {
    val (toT, errorMsg) = NumericField.conversionFunction[T]
    strs match {
      case Seq() => Right(None)
      case Seq(s) => try {
        Right(Some(toT(s.trim)))
      } catch {
        case e: NumberFormatException => Left(ValidationError(errorMsg(s)))
      }
      case _ => Left(ValidationError("Expected a single value, got multiples."))
    }
  }
  
  def validators = OptionValidator(NumericField.minAndMaxValidators(minValue, maxValue))
}

object NumericField {
  def minAndMaxValidators[T](minValue: Option[T], maxValue: Option[T])(implicit n: Numeric[T]): List[Validator[T]] = {
    val min = minValue match {
      case None => Nil
      case Some(min) => List(new MinValueValidator[T](min, (x => "This value must be at least %s.".format(min))))
    }
    val max = maxValue match {
      case None => Nil
      case Some(max) => List(new MaxValueValidator[T](max, (x => "This value must be at most %s.".format(max))))
    }
    min ++ max
  }
  
  def conversionFunction[T](implicit man: Manifest[T]): ((String => T), (String => String)) = {
    if (man.runtimeClass == classOf[Int]) {
      ((s: String) => s.toInt.asInstanceOf[T], (s: String) => "This value must be a positive or negative whole number.")
    } else if (man.runtimeClass == classOf[Double]) {
      ((s: String) => s.toDouble.asInstanceOf[T], (s: String) => "This value must be a number.")
    } else {
      throw new Exception("Numeric field only supported for Int and Double.")
    }
  }
}