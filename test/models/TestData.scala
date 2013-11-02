package models

import java.io.File
import java.sql._

import models._
import org.joda.time.LocalDate

import javax.jdo.annotations.Inheritance
import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.Unique
import scalajdo.DataStore
import scalajdo.ScalaPersistenceManager

import auth.User

object TestData {
  def load(debug: Boolean = false) {
    val dbFile = new File("data.h2.db")
    if (dbFile.exists) dbFile.delete()
    loadGlmlData(debug)
    DataStore.pm.close()
  }

  def loadGlmlData(debug: Boolean = false) {
    if(debug) println("Creating glml...")
    
    // Years
    if(debug) println("Creating years...")
    val year2011 = new Year(2011)
    val year2012 = new Year(2012)
    
    // Districts
    if(debug) println("Creating districs...")
    val district1in11 = new District("0", year2011)
    val district1 = new District("1", year2012)
    val district2 = new District("2", year2012)
    
    // Schools
    if(debug) println("Creating schools...")
    val school1 = new School("duPont Manual")
    val school2 = new School("St. Xavier")
    val school3 = new School("Ballard")
    val school4 = new School("St. Francis")
    
    // Coaches
    if(debug) println("Creating coaches...")
    val coach1 = new User("coach1", "Kris", "Tatro", email="tatro@abc.com", password="pw")
    val coach2 = new User("coach2", "Ron", "Newton", password="pw")
    val coach3 = new User("coach3", "Mary", "Eschels", email="mary@abc.com", password="pw")
    val coach4 = new User("coach4", "Emily", "David")
    val coach5 = new User("coach5", "Lesli", "Poynter", email="lesli@abc.com")
    val coach6 = new User("coach6", "Liz", "Minton")

    // School IDs
    if(debug) println("Creating schools IDs...")
    val schId1in11 = new SchoolId("01", school1, district1in11, Set(coach1))
    val schId1 = new SchoolId("01", school1, district1, Set(coach1))
    val schId2 = new SchoolId("02", school2, district1, Set(coach2))
    val schId3 = new SchoolId("03", school3, district1, Set(coach3, coach4, coach5))
    val schId4 = new SchoolId("04", school4, district2, Set(coach6))

    // Students
    if(debug) println("Creating students...")
    val student1 = new Student("Liu", "Andrew", None, None)
    val student2 = new Student("Zhang", "Yue", None, None)
    val student3 = new Student("Lian", "Serena", None, None)
    val student4 = new Student("Kook", "Tae Lim", None, None)
    val student5 = new Student("Thieneman", "Tanner", None, None)
    val student6 = new Student("Du", "Jian", None, None)
    val student7 = new Student("Finklestein", "Teddy", None, None)
    val student8 = new Student("Miller", "Jim",  None, None)
    val student9 = new Student("Hu", "Jiahui",  None, None)
    
    // Student IDs
    if(debug) println("Creating student IDs...")
    val stuId1 = new StudentId("101401", student1, schId1, 12)
    val stuId2 = new StudentId("101101", student2, schId1, 9)
    val stuId3 = new StudentId("101402", student3, schId1, 12)
    val stuId4 = new StudentId("101201", student4, schId1, 10)
    val stuId5 = new StudentId("102401", student5, schId2, 12)
    val stuId6 = new StudentId("103401", student6, schId3, 12)
    val stuId7 = new StudentId("204401", student7, schId4, 12)
    println("flag")
    val stuId8 = new StudentId("001201", student8, schId1in11, 10)
    val stuId9 = new StudentId("001202", student9, schId1in11, 10)

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
    val test5 = new Test(date1, stuId3, 50)
    val test6 = new Test(date2, stuId3, 50)
    val test7 = new Test(date1, stuId4, 40)
    val test8 = new Test(date2, stuId4, 40)
    val test9 = new Test(date1, stuId5, 30)
    val test10 = new Test(date2, stuId5, 30)
    val test11 = new Test(date1, stuId6, 22.5)
    val test12 = new Test(date2, stuId6, 20)
    val test13 = new Test(date1, stuId7, 10)
    val test14 = new Test(date2, stuId7, 10)
    
    // Making everything persistent
    if(debug) println("Making objects persistent...")
    DataStore.withTransaction{ pm => 
      pm.makePersistent(year2011)
      pm.makePersistent(year2012)
      pm.makePersistent(district1)
      pm.makePersistent(district2)
      pm.makePersistent(district1in11)
      pm.makePersistent(school1)
      pm.makePersistent(school2)
      pm.makePersistent(school3)
      pm.makePersistent(school4)
      pm.makePersistent(coach1)
      pm.makePersistent(coach2)
      pm.makePersistent(coach3)
      pm.makePersistent(coach4)
      pm.makePersistent(coach5)
      pm.makePersistent(coach6)
      pm.makePersistent(schId1)
      pm.makePersistent(schId1in11)
      pm.makePersistent(schId2)
      pm.makePersistent(schId3)
      pm.makePersistent(schId4)
      pm.makePersistent(student1)
      pm.makePersistent(student2)
      pm.makePersistent(student3)
      pm.makePersistent(student4)
      pm.makePersistent(student5)
      pm.makePersistent(student6)
      pm.makePersistent(student7)
      pm.makePersistent(student8)
      pm.makePersistent(student9)
      pm.makePersistent(stuId1)
      pm.makePersistent(stuId2)
      pm.makePersistent(stuId3)
      pm.makePersistent(stuId4)
      pm.makePersistent(stuId5)
      pm.makePersistent(stuId6)
      pm.makePersistent(stuId7)
      pm.makePersistent(stuId8)
      pm.makePersistent(stuId9)
      pm.makePersistent(date1)
      pm.makePersistent(date2)
      pm.makePersistent(test1)
      pm.makePersistent(test2)
      pm.makePersistent(test3)
      pm.makePersistent(test4)
      pm.makePersistent(test5)
      pm.makePersistent(test6)
      pm.makePersistent(test7)
      pm.makePersistent(test8)
      pm.makePersistent(test9)
      pm.makePersistent(test10)
      pm.makePersistent(test11)
      pm.makePersistent(test12)
      pm.makePersistent(test13)
      pm.makePersistent(test14)
    }
  }
  
}