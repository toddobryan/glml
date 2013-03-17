/*
import scala.language.experimental.macros

import scala.reflect.macros._

object JdoMacro {
  def makeField[T](name: String, initVal: T) = macro FieldImpl.makeImpl[T]
}

object FieldImpl {
  def makeImpl[T](c: Context)(name: c.Expr[String], initVal: c.Expr[T]) = {
    import c.universe._
    /* Expr(Block(List
    (ValDef(Modifiers(MUTABLE), newTermName(name.splice), Ident(T), Literal(Constant(initVal.splice)))), Literal(Constant(()))))
  	*/
  }
}

*/
