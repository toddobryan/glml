package models.files

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import org.joda.time.DateTime
import scalajdo._
import models.auth._

@PersistenceCapable(detachable="true")
class File {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _title: String = _
  @Persistent(defaultFetchGroup = "true")
  private[this] var _owner: User = _
  @Column(length=1048576) // 1MB
  private[this] var _content: String = _
  @Persistent(defaultFetchGroup= "true")
  private[this] var _lastModified: java.sql.Timestamp = _
  
  def this(title: String, owner: User, content: String, lastModified: Option[DateTime] = None) {
    this()
    _title = title
    _owner = owner
    _content = content
    lastModified match {
      case None => _lastModified = null
      case Some(date) => lastModified_=(date)
    }
  }
  
  def id: Long = _id
  
  def title: String = _title
  def title_=(theTitle: String) = (_title = theTitle)
  
  def owner: User = _owner
  def owner_=(theOwner: User) = (_owner = theOwner)
  
  def content: String = _content
  def content_=(theContent: String) = (_content = theContent)
  
  def lastModified: Option[DateTime] = if(_lastModified == null) None else Some(new DateTime(_lastModified.getTime))
  def lastModified_=(theDate: DateTime) = (_lastModified = new java.sql.Timestamp(theDate.getMillis()))
  
  override def toString = {
    "%s -- %s".format(title, owner)
  }
  
  def recentSort(file: File): Boolean = {
    lastModified match {
      case None => false
      case Some(dt1) => file.lastModified match {
        case None => true
        case Some(dt2) => dt1.getMillis > dt2.getMillis
      }
    }
  }
  
  def timeString = lastModified match {
    case None => ""
    case Some(date) => {
      val now = DateTime.now()
      val timeSince = now.getMillis - date.getMillis
      val millInADay = 86400000
      if(timeSince < millInADay && now.getDayOfMonth == date.getDayOfMonth)
        "%d:%02d".format(date.getHourOfDay, date.getMinuteOfHour)
      else if (timeSince < 2 * millInADay && now.getDayOfMonth - 1 == date.getDayOfMonth)
        "Yesterday" + "%d:%02d".format(date.getHourOfDay, date.getMinuteOfHour)
      else
        "%02d-%02d".format(date.getDayOfMonth, date.getMonthOfYear)
    }
  }
}
  
object File {
	def getById(id: Long): Option[File] = {
	  val cand = QFile.candidate
	  DataStore.pm.query[File].filter(cand.id.eq(id)).executeOption
	}
	
	def getByOwner(owner: User): List[File] = {
	  val cand = QFile.candidate
	  DataStore.pm.query[File].filter(cand.owner.eq(owner)).executeList
	}
	
	def mostRecentFour(owner: User): List[File] = {
	  getByOwner(owner).sortWith(_ recentSort _).take(4)
	}
	
	def fileSidebar(files: List[File]): scala.xml.Elem = {
	  <div id="files">
	  {for(file <- files) yield {
	    <li>
            <small>
		    <a href={"/file/" + file.id}>
		    {file.title}
	    	<br />
            <div class="muted">{file.timeString}</div>
            </a>
            </small>
	    </li>
	  }}
	  </div>
	}
}

trait QFile extends PersistableExpression[File] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _title: StringExpression = new StringExpressionImpl(this, "_title")
  def title: StringExpression = _title
  
  private[this] lazy val _owner: ObjectExpression[User] = new ObjectExpressionImpl[User](this, "_owner")
  def owner: ObjectExpression[User] = _owner
  
  private[this] lazy val _content: StringExpression = new StringExpressionImpl(this, "_content")
  def content: StringExpression = _content
}

object QFile {
  def apply(parent: PersistableExpression[File], name: String, depth: Int): QFile = {
    new PersistableExpressionImpl[File](parent, name) with QFile
  }
  
  def apply(cls: Class[File], name: String, exprType: ExpressionType): QFile = {
    new PersistableExpressionImpl[File](cls, name, exprType) with QFile
  }
  
  private[this] lazy val jdoCandidate: QFile = candidate("this")
  
  def candidate(name: String): QFile = QFile(null, name, 5)
  
  def candidate(): QFile = jdoCandidate
  
  def parameter(name: String): QFile = QFile(classOf[File], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QFile = QFile(classOf[File], name, ExpressionType.VARIABLE)
}
