package models

import scala.collection.JavaConverters._

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

import auth.{User, QUser}

@PersistenceCapable(detachable="true")
@Uniques(Array(
    new Unique(name="DISTRICT_WITH_SCHOOL", members=Array("_district", "_school")),
    new Unique(name="DISTRICT_WITH_GLML_ID", members=Array("_district", "_glmlId"))))
class SchoolId {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Column(allowsNull="false", length=2)
  private[this] var _glmlId: String = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _school: School = _
  @Persistent(defaultFetchGroup="true")
  private[this] var _district: District = _
  @Element(types=Array(classOf[User]))
  @Join
  private[this] var _coaches: java.util.Set[User] = _
 
  def this(glmlId: String, school: School, district: District, coaches: Set[User]) = {
    this()
    glmlId_=(glmlId)
    school_=(school)
    district_=(district)
    coaches_=(coaches)
  }
  
  def id: Long = _id
  
  def glmlId: String = _glmlId
  def glmlId_=(theId: String) { _glmlId = theId }
  
  def school: School = _school
  def school_=(theSchool: School) { _school = theSchool }
  
  def district: District = _district
  def district_=(theDistrict: District) { _district = theDistrict }
  
  def coaches: Set[User] = _coaches.asScala.toSet
  def coaches_=(theCoaches: Set[User]) { _coaches = theCoaches.asJava }
  
  override def toString: String = "SchoolId(%d)".format(id)
  
  def getCumulativeScore(): BigDecimal = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QStudentId.candidate
    pm.query[StudentId].filter(cand.schoolId.eq(this)).executeList().foldLeft(BigDecimal(0.0))((sum, studentId) => studentId.getCumulativeScore() + sum)
  }
  
  def coachesNames: Set[String] = {
    coaches map { _.fullName() }
  }
  
  // when implementing in views, disable automatic HTML escaping using @Html(coachesStr())
  // renamed from coachesStr
  def coachesEmails: Set[String] = {
    coaches map { (coach: User) =>
      if (coach.email.isDefined) "%s %s - <a href=\"mailto:%s\">%s</a>".format(coach.first, coach.last, coach.email.get, coach.email.get)
      else "%s %s".format(coach.first, coach.last)
    }
  }
  
  // renamed from coachWord
  def coachPlural: String = {
    if (coaches.size == 1) "Coach"
    else "Coaches"
  }
  
  def getValidId(grade: Int): String = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QStudentId.candidate
    val studentIds = pm.query[StudentId].filter(cand.schoolId.eq(this)).executeList().filter(stuId => stuId.grade == grade)
    val unusedIds = List.range(1, 99) diff studentIds.map((stuId: StudentId) => stuId.glmlId.substring(4).toInt)
    val id = unusedIds.min.toString
    
    if (id.length == 1) "0" + id
    else id
  }

}

object SchoolId {
  def getOrCreateAnswerKeySchoolId(maybeYear: Option[Year] = None): SchoolId = {
    val glmlId = StudentId.answerKeyStudentId.substring(1, 3)
    val year = maybeYear.getOrElse(Year.currentYear)
    val cand = QSchoolId.candidate
    val district = District.getOrCreateAnswerKeyDistrict(Some(year))
    val school = School.getOrCreateAnswerKeySchool
    DataStore.pm.query[SchoolId].filter(cand.school.eq(school).and(
        cand.district.eq(district))).executeOption() match {
      case Some(s) => s
      case None => {
        val newSchoolId = new SchoolId(glmlId, school, district, null)
        DataStore.pm.makePersistent(newSchoolId)
        newSchoolId
      }
    }
  }
    
  def getCurrentSchoolId(user: User, maybeYear: Option[Year] = None): SchoolId = {
    val year = maybeYear.getOrElse(Year.currentYear)
    val cand = QSchoolId.candidate
    val varble = QDistrict.variable("District")
    val schoolIds = DataStore.pm.query[SchoolId].filter(cand.district.eq(varble).and(
        varble.year.eq(year))).executeList().filter(_.coaches.contains(user))
    if (!schoolIds.isEmpty) schoolIds(0) else null
  }
}

trait QSchoolId extends PersistableExpression[SchoolId] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _glmlId: StringExpression = new StringExpressionImpl(this, "_glmlId")
  def glmlId: StringExpression = _glmlId
  
  private[this] lazy val _school: ObjectExpression[School] = new ObjectExpressionImpl[School](this, "_school")
  def school: ObjectExpression[School] = _school
  
  private[this] lazy val _district: ObjectExpression[District] = new ObjectExpressionImpl[District](this, "_district")
  def district: ObjectExpression[District] = _district
  
  private[this] lazy val _coaches: CollectionExpression[java.util.Set[User], User] = new CollectionExpressionImpl[java.util.Set[User], User](this, "_coaches")
  def coaches: CollectionExpression[java.util.Set[User], User] = _coaches
}

object QSchoolId {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QSchoolId = {
    new PersistableExpressionImpl[SchoolId](parent, name) with QSchoolId
  }
  
  def apply(cls: Class[SchoolId], name: String, exprType: ExpressionType): QSchoolId = {
    new PersistableExpressionImpl[SchoolId](cls, name, exprType) with QSchoolId
  }
  
  private[this] lazy val jdoCandidate: QSchoolId = candidate("this")
  
  def candidate(name: String): QSchoolId = QSchoolId(null, name, 5)
  
  def candidate(): QSchoolId = jdoCandidate
  
  def parameter(name: String): QSchoolId = QSchoolId(classOf[SchoolId], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QSchoolId = QSchoolId(classOf[SchoolId], name, ExpressionType.VARIABLE)
}
