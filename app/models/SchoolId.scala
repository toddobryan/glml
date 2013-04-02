package models

import scala.collection.JavaConverters._

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import auth.User

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
    BigDecimal(0.0) //TODO
  }
  
  /*
  def coachesNames //TODO
  def coachesStr //TODO
  def coachWord //TODO
  def getValidId //TODO
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
