package models

import java.io.File
import java.sql._

import models._
import org.joda.time.LocalDate

import javax.jdo.annotations.Inheritance
import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.Unique
import util.DataStore
import util.ScalaPersistenceManager

object TestData {
  def load(debug: Boolean = false) {
    val dbFile = new File("data.h2.db")
    dbFile.delete()
    DataStore.withManager { implicit pm =>
      loadGlmlData(debug)
      pm.close()
    }
  }

  def loadGlmlData(debug: Boolean = false)(implicit pm: ScalaPersistenceManager) {
    if(debug) println("Creating glml...")
    loadDistricts(debug)
  }
  
  def loadDistricts(debug: Boolean)(implicit pm: ScalaPersistenceManager) {
    // Years
    if(debug) println("Creating years...")
    val year2011 = new Year(2011)
    val year2012 = new Year(2012)
    
    // Districts
    if(debug) println("Creating districs...")
    val district1 = new District("District 1", year2012)
    val district2 = new District("District 2", year2012)
    
    // Schools
    if(debug) println("Creating schools...")
    val school1 = new School("duPont Manual")
    val school2 = new School("St. Xavier")
    val school3 = new School("Ballard")
    val school4 = new School("St. Francis")
    
    // Coaches
    if(debug) println("Creating coaches...")
    val coach1 = new User("coach1", Some("Becky"), Some("Wahl"), Some("Email"), Some("pw"))
    val coach2 = new User("coach2", Some("Mary"), Some("Eschels"), Some("Email"), Some("pw"))
    val coach3 = new User("coach3", Some("Emily"), Some("David"), None, None)
    val coach4 = new User("coach4", Some("Lesli"), Some("Poynter"), Some("Email"), None)
    val coach5 = new User("coach5", Some("Ron"), Some("Newton"), None, Some("pw"))
    val coach6 = new User("coach6", Some("Liz"), Some("Minton"), None, None)

    // School IDs
    if(debug) println("Creating schools IDs...")
    val schId1 = new SchoolId("999999", school1, district1, Set(coach1))
    val schId2 = new SchoolId("888888", school2, district1, Set(coach5))
    val schId3 = new SchoolId("777777", school3, district1, Set(coach2, coach3, coach4))
    val schId4 = new SchoolId("666666", school4, district2, Set(coach6))

    // Students
    if(debug) println("Creating students...")
    val student1 = new Student("Liu", "Andrew", None, None)
    val student2 = new Student("Thieneman", "Tanner", None, None)
    val student3 = new Student("Lian", "Serena", None, None)
    val student4 = new Student("Kook", "Tae Lim", None, None)
    val student5 = new Student("Zhang", "Yue", None, None)
    val student6 = new Student("Du", "Jian", None, None)
    val student7 = new Student("Finklestein", "Teddy", None, None)
    
    // Student IDs
    if(debug) println("Creating student IDs...")
    val stuId1 = new StudentId("000001", student1, schId1, 12)
    val stuId2 = new StudentId("000002", student2, schId2, 12)
    val stuId3 = new StudentId("000003", student3, schId1, 12)
    val stuId4 = new StudentId("000004", student4, schId1, 10)
    val stuId5 = new StudentId("000005", student5, schId1, 9)
    val stuId6 = new StudentId("000006", student6, schId3, 12)
    val stuId7 = new StudentId("000007", student7, schId4, 12)

    // Test Dates
    if(debug) println("Creating test dates...")
    val date1 = new TestDate(new LocalDate(2012, 11, 13), year2012)
    val date2 = new TestDate(new LocalDate(2013, 1, 15), year2012)
    
    // Tests
    if(debug) println("Creating tests...")
    val test1 = new Test(date1, stuId1, 75)
    val test2 = new Test(date2, stuId1, 75)
    val test3 = new Test(date1, stuId2, 60)
    val test4 = new Test(date2, stuId2, 60)
    val test5 = new Test(date1, stuId3, 75)
    val test6 = new Test(date2, stuId3, 70)
    val test7 = new Test(date1, stuId4, 70)
    val test8 = new Test(date2, stuId4, 70)
    val test9 = new Test(date1, stuId5, 75)
    val test10 = new Test(date2, stuId5, 75)
    val test11 = new Test(date1, stuId6, 62.5)
    val test12 = new Test(date2, stuId6, 60)
    val test13 = new Test(date1, stuId7, 75)
    val test14 = new Test(date2, stuId7, 65)
  }
  
}