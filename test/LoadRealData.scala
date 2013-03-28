import org.joda.time.{LocalDate, DateTime}
import scalajdo.DataStore

import models.auth.User
import models._

object LoadRealData {
  def loadYears(debug: Boolean = false) {
    if (debug) println("Adding Year models to the database...")
    val years = List(
      new Year(2012),
      new Year(2011),
      new Year(2010),
      new Year(2009),
      new Year(2008),
      new Year(2007)
    )
    years.foreach(DataStore.pm.makePersistent(_))
  }

  def loadDistricts(debug: Boolean = false) {
    if (debug) println("Adding District models to the database...")
    val yearMap: Map[Int, Year] = DataStore.pm.query[Year].executeList().map((y: Year) => y.start -> y).toMap
    val districts = List(
      new District("1", yearMap(2012)),
      new District("2", yearMap(2012)),
      new District("3", yearMap(2012)),
      new District("4", yearMap(2012)),
      new District("9", yearMap(2012)),
      new District("1", yearMap(2011)),
      new District("2", yearMap(2011)),
      new District("3", yearMap(2011)),
      new District("4", yearMap(2011)),
      new District("9", yearMap(2011)),
      new District("1", yearMap(2010)),
      new District("2", yearMap(2010)),
      new District("3", yearMap(2010)),
      new District("4", yearMap(2010)),
      new District("9", yearMap(2010)),
      new District("1", yearMap(2009)),
      new District("2", yearMap(2009)),
      new District("3", yearMap(2009)),
      new District("4", yearMap(2009)),
      new District("9", yearMap(2009)),
      new District("1", yearMap(2008)),
      new District("2", yearMap(2008)),
      new District("3", yearMap(2008)),
      new District("4", yearMap(2008)),
      new District("9", yearMap(2008)),
      new District("1", yearMap(2007)),
      new District("2", yearMap(2007)),
      new District("3", yearMap(2007)),
      new District("4", yearMap(2007)),
      new District("9", yearMap(2007))
    )
    districts.foreach(DataStore.pm.makePersistent(_))
  }

}