package webscala

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.Results._
import java.io.StringWriter
import java.io.PrintWriter
import scala.annotation.Annotation

class HtmlRepl {
  
}

object HtmlRepl {
  lazy val out = new StringWriter()
  
  val repl = {
    val settings = new Settings
    settings.embeddedDefaults(new ReplClassLoader(settings.getClass().getClassLoader()))
    val theRepl = new IMain(settings, new PrintWriter(out))
    theRepl.addImports("scala.language.ImplicitConversions")
    theRepl.addImports("image._")
    theRepl
  }
}

