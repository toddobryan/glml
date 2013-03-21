package models

import org.joda.time.LocalDate
import org.scalatest.FunSuite

import scalajdo.DataStore
import scalajdo.ScalaPersistenceManager

class glmlTests extends FunSuite {
    val pm = DataStore.pm
    
    test("District.getTopSchools") {
      val cand = QDistrict.candidate
      val d1 = pm.query[District].filter(cand.name.eq("1")).executeOption().get
      assert(d1.getTopSchools(None) === List("test"))
	}
}