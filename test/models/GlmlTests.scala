package models

import org.joda.time.LocalDate
import org.scalatest.FunSuite

import scalajdo.DataStore
import scalajdo.ScalaPersistenceManager

import java.math.BigDecimal
import org.joda.time.{DateTime, LocalDate}

import auth.{User, QUser}

class glmlTests extends FunSuite {
  val pm = DataStore.pm
  
  val cand = QDistrict.candidate
  val cand2 = QUser.candidate
  val d1 = pm.query[District].filter(cand.name.eq("1")).executeOption().get
  val coach1 = pm.query[User].executeList()
  
  test("District.getTopSchools cumulative") {
    val t1 = List(("duPont Manual", new BigDecimal(450), Set(coach1(0).username)), 
        ("St. Xavier", new BigDecimal(60), Set(coach1(1).username)),
        ("Ballard", new BigDecimal(42.5), Set(coach1(2).username, coach1(3).username, coach1(4).username)))
    assert(d1.getTopSchools(None).map(a => (a._1, a._2, a._3.map(_.username))) === t1)
  }
  
  test("District.getTopSchools single date") {
    val cand3 = QTestDate.candidate
    val date1 = pm.query[TestDate].executeList().filter(_.date == new LocalDate(2012, 11, 13))(0)
    val t2 = List(("duPont Manual", new BigDecimal(225), Set(coach1(0).username)), 
        ("St. Xavier", new BigDecimal(30), Set(coach1(1).username)),
        ("Ballard", new BigDecimal(22.5), Set(coach1(4).username, coach1(3).username, coach1(2).username)))
    assert(d1.getTopSchools(Some(date1)).map(a => (a._1, a._2, a._3.map(_.username))) === t2)
  }
  
  //TODO: StudentId.getCumulativeScore()
}