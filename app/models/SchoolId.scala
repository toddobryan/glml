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
  private[this] var _school: School = _
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
  def coachesEmails(): Set[String] = {
    coaches map { (coach: User) =>
      if (!coach.email.isEmpty) "%s %s - <a href=\"mailto:%s\">%s</a>".format(coach.first, coach.last, coach.email.get, coach.email.get)
      else "%s %s".format(coach.first, coach.last)
    }
  }
  
  // renamed from coachWord
  def coachPlural: String = {
    if (coaches.size == 1) "Coach"
    else "Coaches"
  }
  
  /*
  def getValidId //TODO
  */
  
  /*
    def get_valid_id(self, grade):
        student_ids = StudentID.objects.filter(school_id=self, grade=grade)
        used_ids = [int(student_id.glml_id[4:]) for student_id in student_ids]
        all_ids = range(1, 100)
        [all_ids.remove(id) for id in used_ids if id in all_ids]
        id = unicode(min(all_ids))
        if len(id) == 1:
            return u'0%s' % id
        return id
   */

}

object SchoolId {
  def getOrCreateAnswerSchoolId(year: Option[Year]): SchoolId = {
    null //TODO
  }
  
  def getCurrentSchoolId(year: Option[Year]): SchoolId = {
    null //TODO
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
