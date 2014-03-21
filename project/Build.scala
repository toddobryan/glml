import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "glml"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    "org.scala-lang" % "scala-compiler" % "2.10.3",
    "org.scala-lang" % "scala-swing" % "2.10.3",
    "org.scala-lang" % "scala-actors" % "2.10.3",
    "org.scalatest" %% "scalatest" % "2.1.1" % "test",
    "commons-codec" % "commons-codec" % "1.9",
    "javax.mail" % "mail" % "1.4.7",
    "org.mindrot" % "jbcrypt" % "0.3m",
    
    "org.apache.poi" % "poi" % "3.9",
    "org.apache.poi" % "poi-ooxml" % "3.9",
    "org.apache.poi" % "poi-ooxml-schemas" % "3.9",

    "com.h2database" % "h2" % "1.3.175",
    "javax.jdo" % "jdo-api" % "3.0",
    "org.datanucleus" % "datanucleus-core" % "3.2.13",
    "org.datanucleus" % "datanucleus-api-jdo" % "3.2.8",
    "org.datanucleus" % "datanucleus-enhancer" % "3.1.1",
    "org.datanucleus" % "datanucleus-jdo-query" % "3.0.2",
    "org.datanucleus" % "datanucleus-rdbms" % "3.2.12",
    "org.datanucleus" % "datanucleus-jodatime" % "3.2.1",

    "org.dupontmanual" %% "dm-forms" % "0.2-SNAPSHOT",
    
    "com.typesafe.akka" %% "akka-actor" % "2.0.3"
  )


    val main = play.Project(appName, appVersion, appDependencies).settings(
      Seq(parallelExecution in Test := false,
          testOptions in Test += Tests.Argument("-oDF"),
          scalaVersion := "2.10.3",
          javacOptions ++= Seq("-source", "1.6", "-target", "1.6", "-bootclasspath", "/usr/lib/jvm/java-6-oracle/jre/lib/rt.jar"),
          scalacOptions ++= Seq("-deprecation", "-feature"),
          routesImport += "scala.language.reflectiveCalls",
          resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots") ++
      Nucleus.settings: _*)
}

object Nucleus {
  
  // defines our own ivy config that wont get packaged as part of your app
  // notice that it extends the Compile scope, so we inherit that classpath
  val Config = config("nucleus") extend Compile

  // our task
  val enhance = TaskKey[Unit]("enhance")
  
  // implementation
  val settings:Seq[Project.Setting[_]] = Seq(
    // let ivy know about our "nucleus" config
    ivyConfigurations += Config,
    // add the enhancer dependency to our nucleus ivy config
    libraryDependencies += "org.datanucleus" % "datanucleus-enhancer" % "3.1.1" % Config.name,
    // fetch the classpath for our nucleus config
    // as we inherit Compile this will be the fullClasspath for Compile + "datanucleus-enhancer" jar 
    //fullClasspath in Config <<= (classpathTypes in enhance, update).map{(ct, report) =>
    //  Classpaths.managedJars(Config, ct, report)
    //},
    // add more parameters as your see fit
    //enhance in Config <<= (fullClasspath in Config, runner, streams).map{(cp, run, s) =>
    enhance <<= Seq(compile in Compile).dependOn,
    enhance in Config <<= (dependencyClasspath in Compile, classDirectory in Compile, runner, streams)
        map { (deps, classes, run, s) => 

      // Properties
      val classpath = (deps.files :+ classes)
      
      
      // the classpath is attributed, we only want the files
      //val classpath = cp.files
      // the options passed to the Enhancer... 
      val options = Seq("-v") ++ findAllClassesRecursively(classes).map(_.getAbsolutePath)
      Thread.sleep(1000)
      
      // run returns an option of errormessage
      val result = run.run("org.datanucleus.enhancer.DataNucleusEnhancer", classpath, options, s.log)
      // if there is an errormessage, throw an exception
      result.foreach(sys.error)
    }
  )
  
  def findAllClassesRecursively(dir: File): Seq[File] = {
    if (dir.isDirectory) {
      val files = dir.listFiles
      files.flatMap(findAllClassesRecursively(_)) 
    } else if (dir.getName.endsWith(".class")) {
      Seq(dir)
    } else {
      Seq.empty
    }
  }
}
