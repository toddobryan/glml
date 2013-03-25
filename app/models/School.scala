package models

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.ScalaPersistenceManager
import scalajdo.DataStore

@PersistenceCapable(detachable="true")
class School {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  
  @Column(allowsNull="false")
  private[this] var _name: String = _
  
  def this(name: String) = {
    this()
    name_=(name)
  }
  
  def id: Long = _id
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def coaches: List[User] = {
    val pm: ScalaPersistenceManager = DataStore.pm
    val cand = QSchoolId.candidate
    // pm.query[SchoolId].filter()
    
    Nil //TODO
  }
  
  /*
   def coaches(self):
        coaches = set()
        for school_id in self.schoolid_set.all():
            for coach in school_id.coaches.all():
                coaches.add(coach)
        return list(coaches)
   */
  
  override def toString: String = name
}

object School {
  def getOrCreateAnswerSchool: School = {
    null //TODO
  }
}

trait QSchool extends PersistableExpression[School] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
}

object QSchool {
  def apply(parent: PersistableExpression[School], name: String, depth: Int): QSchool = {
    new PersistableExpressionImpl[School](parent, name) with QSchool
  }
  
  def apply(cls: Class[School], name: String, exprType: ExpressionType): QSchool = {
    new PersistableExpressionImpl[School](cls, name, exprType) with QSchool
  }
  
  private[this] lazy val jdoCandidate: QSchool = candidate("this")
  
  def candidate(name: String): QSchool = QSchool(null, name, 5)
  
  def candidate(): QSchool = jdoCandidate

  def parameter(name: String): QSchool = QSchool(classOf[School], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QSchool = QSchool(classOf[School], name, ExpressionType.VARIABLE)
}
