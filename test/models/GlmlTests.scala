package models

import org.joda.time.LocalDate
import org.scalatest.FunSuite

import scalajdo.DataStore
import scalajdo.ScalaPersistenceManager
import org.joda.time.{DateTime, LocalDate}

import auth.{User, QUser}

class glmlTests extends FunSuite {
  val pm = DataStore.pm
  
  val cand = QDistrict.candidate
  val cand2 = QUser.candidate
  val d1 = pm.query[District].filter(cand.glmlId.eq("1")).executeOption().get
  val coach1 = pm.query[User].executeList()
  
  test("District.getTopSchools() cumulative score") {
    val t1 = List(("duPont Manual", BigDecimal(450), Set(coach1(0).username)), 
        ("St. Xavier", BigDecimal(60), Set(coach1(1).username)),
        ("Ballard", BigDecimal(42.5), Set(coach1(2).username, coach1(3).username, coach1(4).username)))
    assert(d1.getTopSchools(None) === t1)
  }
  
  test("District.getTopSchools() single date") {
    val cand3 = QTestDate.candidate
    val date1 = pm.query[TestDate].executeList().filter(_.date == new LocalDate(2012, 11, 13))(0)
    val t2 = List(("duPont Manual", BigDecimal(225), Set(coach1(0).username)), 
        ("St. Xavier", BigDecimal(30), Set(coach1(1).username)),
        ("Ballard", BigDecimal(22.5), Set(coach1(4).username, coach1(3).username, coach1(2).username)))
    assert(d1.getTopSchools(Some(date1)) === t2)
  }
  
  //TODO: StudentId.getCumulativeScore()
  //TODO: District.getTopStudents(testDate: Option[TestDate])
  //TODO: SchoolId.getCumulativeScore()
  //TODO: SchoolId.coachesEmails
  //TODO: SchoolId.coachPlural
  //TODO: SchoolId.getValidId(grade: Int)
  //TODO: Test.compareMapping()
  //TODO: Test.rescore()
  //TODO: TestDate.getKey()
  //TODO: TestDate.pdf
  //TODO: getOrCreateAnswerKey x 6
  //TODO: SchoolId.getCurrentSchoolId()
}