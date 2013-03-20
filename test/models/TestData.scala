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
    if(!debug) println("Creating something...")
    loadSomething(debug)
  }
  
  def loadSomething(debug: Boolean)(implicit pm: ScalaPersistenceManager) {
    
    /*
    // teachers
    val mary = new User("mary", "Mary", Some("King"), "Claire", None, Gender.FEMALE, "mary@mary.com", "cla123")
    val maryTeacher = new Teacher(mary, "318508", "4284802048")

    // students
    val jack = new User("jack", "Jack", Some("Oliver"), "Phillips", None, Gender.MALE, "jack@jack.com", "phi123")
    val fitzgerald = new User("fitzgerald", "Fitzgerald", Some("Longfellow"), "Pennyworth", Some("Fitz of Fury"), Gender.MALE, "fitzgerald@fitzgerald.com", "pen123")
    val jackStud = new Student(jack, "3757202948", "425636", 0, "MST")
    val fitzgeraldStud = new Student(fitzgerald, "8340522509", "382085", 4, "VA")

    // guardians
    val reg = new User("reg", "Reginald", None, "Pennyworth", Some("Reg"), Gender.MALE, null, "pen123")
    val regGuardian = new Guardian(reg, Set(fitzgeraldStud))
    */
    
  }
}