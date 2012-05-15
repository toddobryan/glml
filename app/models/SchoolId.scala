package models

import scala.collection.JavaConverters._

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

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
  
  override def toString: String = "%s: %s".format(district.year.slug, school)

}