import scala.io.Source
import scala.xml._
import org.joda.time.{LocalDate, DateTime}
import scalajdo.DataStore
import models._
import models.auth.User

object LoadPostgresData {
  def load(debug: Boolean=true) {
    val dbFile = new java.io.File("data.h2.db")
    if (debug) println("Deleting old database file...")
    if (dbFile.exists) dbFile.delete()
    val doc = XML.load(getClass.getResource("/postgres-data.xml"))
    loadYears(doc \ "years" \ "year", debug)
    loadDistricts(doc \ "districts" \ "district", debug)
    loadSchools(doc \ "schools" \ "school", debug)
    loadStudents(doc \ "students" \ "student", debug)
    loadTestDates(doc \ "testDates" \ "testDate", debug)
    loadCoaches(doc \ "coaches" \ "coach", debug)
    loadSchoolIds(doc \ "schoolIds" \ "schoolId", debug)
    loadStudentIds(doc \ "studentIds" \ "studentId", debug)
    loadTests(doc \ "tests" \ "test", debug)
  }
  
  lazy val yearMap: Map[Int, Year] = DataStore.pm.query[Year].executeList().map((y: Year) => y.start -> y).toMap
  
  lazy val schoolMap: Map[String, School] = DataStore.pm.query[School].executeList().map((s: School) => s.name -> s).toMap
  
  lazy val coachMap: Map[String, User] = DataStore.pm.query[User].executeList().map((u: User) => u.username -> u).toMap
  
  lazy val studentMap: Map[(String, String, Option[String], Option[String]), Student] = DataStore.pm.query[Student].executeList().map((s: Student) => ((s.lastName, s.firstName, s.middleName, s.suffix) -> s)).toMap
  
  lazy val districtMap: Map[(String, Int), District] = DataStore.pm.query[District].executeList().map((d: District) => ((d.glmlId, d.year.start) -> d)).toMap
  
  lazy val schoolIdMap: Map[(Int, String, String), SchoolId] = DataStore.pm.query[SchoolId].executeList().map((s: SchoolId) => (s.district.year.start, s.district.glmlId, s.glmlId) -> s).toMap
  
  lazy val testDateMap: Map[String, TestDate] = DataStore.pm.query[TestDate].executeList().map((td: TestDate) => td.date.toString -> td).toMap
  
  lazy val studentIdMap: Map[(Int, String), StudentId] = DataStore.pm.query[StudentId].executeList().map((s: StudentId) => ((s.schoolId.district.year.start, s.glmlId) -> s)).toMap
  
  def loadYears(years: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating Year model data...")
    years foreach { y =>
      if (debug) println("  Creating year that starts in %d.".format(y.text.toInt))
      DataStore.pm.makePersistent(new Year(y.text.toInt))  
    }
  }
  
  def loadDistricts(districts: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating District model data...")
    districts foreach { d =>
      val districtId = (d \ "glmlId").head.text
      val year = yearMap((d \ "year").head.text.toInt)
      if (debug) println("  Creating district %s for year %s".format(districtId, year.toString))
      DataStore.pm.makePersistent(new District(districtId, year))
    }
  }
  
  def loadSchools(schools: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating School model data...")
    schools foreach { s =>
      if (debug) println("  Creating School model for %s".format(s.text))
      DataStore.pm.makePersistent(new School(s.text))
    }
  }
  
  def studentTuple(student: NodeSeq): (String, String, Option[String], Option[String]) = {
    val first = (student \ "first").text
    val last = (student \ "last").text
    val middle = (student \ "middle").collectFirst({ case n: Node => n.text })
    val suffix = (student \ "suffix").collectFirst({ case n: Node => n.text })
    (last, first, middle, suffix)
  }
  
  def loadStudents(students: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating Student model data...")
    students foreach { s =>
      val (last, first, middle, suffix) = studentTuple(s)
      if (debug) println("  Creating Student %s, %s%s%s".format(
          last, first, middle.map(" " + _).getOrElse(""), suffix.map(", " + _).getOrElse("")))
      DataStore.pm.makePersistent(new Student(last, first, middle, suffix))
    }
  }
  
  def loadTestDates(tds: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating TestDate model data...")
    tds foreach { td =>
      val date = LocalDate.parse((td \ "date").text)
      val year = yearMap((td \ "year").text.toInt)
      if (debug) println("  Creating TestDate for %s in year %s".format(date.toString, year.toString))
      DataStore.pm.makePersistent(new TestDate(date, year))
    }
  }
  
  def coachTuple(coach: NodeSeq): (String, String, String, Boolean, Boolean, DateTime, Option[DateTime], String) = {
    val username = (coach \ "username").text
    val first = (coach \ "first").text
    val last = (coach \ "last").text
    val isActive = (coach \ "isActive").text.toBoolean
    val isSuperUser = (coach \ "isSuperUser").text.toBoolean
    val dateJoined = DateTime.parse((coach \ "dateJoined").text.replace(' ', 'T'))
    val lastLogin = (coach \ "lastLogin").collectFirst({ case n: Node => DateTime.parse(n.text.replace(' ', 'T')) })
    val email = (coach \ "email").text
    (username, first, last, isActive, isSuperUser, dateJoined, lastLogin, email)
  }
  
  def loadCoaches(coaches: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating Coach model data...")
    coaches foreach { c =>
      val (username, first, last, isActive, isSuperUser, dateJoined, lastLogin, email) = coachTuple(c)
      if (debug) println("  Creating Coach %s = %s, %s.".format(username, last, first))
      DataStore.pm.makePersistent(new User(username, first, last, isActive, isSuperUser, dateJoined, lastLogin, email))
    }
  }
  
  def loadSchoolIds(ids: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating SchoolId model data...")
    ids foreach { id =>
      val glmlId = (id \ "glmlId").text
      val school = schoolMap((id \ "school").text)
      val district = districtMap((id \ "district").text, (id \ "year").text.toInt)
      val coaches: Set[User] = if ((id \ "coaches").text != "") {
        (id \ "coaches").text.split(",").toList.map(coachMap(_)).toSet
      } else {
        Set.empty
      }
      if (debug) println("  Creating SchoolId for %s, district %s".format(school, district))
      DataStore.pm.makePersistent(new SchoolId(glmlId, school, district, coaches))
    }
  }
  
  def schoolIdTuple(schoolId: NodeSeq): (Int, String, String) = {
    val year = (schoolId \ "year").text.toInt
    val districtId = (schoolId \ "district").text
    val glmlId = (schoolId \ "glmlId").text
    (year, districtId, glmlId)
  }
  
  def loadStudentIds(ids: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating StudentId model data...")
    ids foreach { id => 
      val glmlId = (id \ "glmlId").text
      val student = studentMap(studentTuple(id \ "student"))
      val schoolId = schoolIdMap(schoolIdTuple(id \ "schoolId"))
      val grade = (id \ "grade").text.toInt
      if (debug) println("  Creating student id for %s (%s)".format(glmlId, schoolId.toString))
      DataStore.pm.makePersistent(new StudentId(glmlId, student, schoolId, grade))
    }
  }
  
  def addQuestions(test: Test, answers: String) {
    (1 to 24).foreach { (i: Int) =>
      val ans = answers.substring(i - 1, i)
      val ansToSave = if ("ABCDE".indexOf(ans) > -1) ans else ""
      val q = new Question(i, ansToSave, test)
      DataStore.pm.makePersistent(q)
    }
  }
  
  def loadTests(tests: NodeSeq, debug: Boolean = true) {
    if (debug) println("Creating Test model data...")
    tests foreach { t =>
      val date = (t \ "testDate").text
      val year = (t \ "year").text.toInt
      val glmlId = (t \ "studentGlmlId").text
      val score = BigDecimal((t \ "score").text)
      if (debug) println("  Creating test for %s, student id %s, score=%.2f".format(date, glmlId, score.toDouble))
      val test = DataStore.pm.makePersistent(new Test(testDateMap(date), studentIdMap(year, glmlId), score))
      addQuestions(test, (t \ "answers").text)
    }
  }
}