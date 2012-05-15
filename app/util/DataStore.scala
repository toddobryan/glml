package util

import scala.collection.JavaConversions._
import javax.jdo.JDOHelper
import org.datanucleus.api.jdo.JDOPersistenceManager
import org.datanucleus.query.typesafe.{BooleanExpression, OrderExpression, TypesafeQuery}
import javax.jdo.Transaction
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory
import javax.jdo.PersistenceManagerFactory
import org.datanucleus.api.jdo.query.JDOTypesafeQuery
import javax.jdo.PersistenceManager
import javax.jdo.spi.PersistenceCapable

object DataStore {
  private[this] var _pmf: Option[JDOPersistenceManagerFactory] = None

  def pmf: JDOPersistenceManagerFactory = {
    if (!_pmf.isDefined) {
      _pmf = Some(JDOHelper.getPersistenceManagerFactory("datastore.props").asInstanceOf[JDOPersistenceManagerFactory])
    }
    _pmf.get
  }
  
  def close() {
    if (_pmf.isDefined) {
      _pmf.get.close()
      _pmf = None
    }
  }

  def getPersistenceManager(): ScalaPersistenceManager = new ScalaPersistenceManager(pmf.getPersistenceManager.asInstanceOf[JDOPersistenceManager])
  
  def withManager[A](block: (ScalaPersistenceManager => A)): A = {
    implicit val pm = getPersistenceManager()
    try {
      block(pm)
    }
  }
  
  def withTransaction[A](block: (ScalaPersistenceManager => A)): A = {
	implicit val pm = getPersistenceManager()
    pm.beginTransaction()
    val r = block(pm)
    pm.commitTransactionAndClose()
    r
  }
}

class ScalaPersistenceManager(val jpm: JDOPersistenceManager) {
  def beginTransaction() {
    jpm.currentTransaction.begin()
  }
  
  def commit() {
    jpm.currentTransaction.commit()
  }
  
  def commitTransaction() {
    try {
      jpm.currentTransaction.commit()
    } finally {
      if (jpm.currentTransaction.isActive) {
        jpm.currentTransaction.rollback()
      }
    }
  }

  def commitTransactionAndClose() {
    try {
      jpm.currentTransaction.commit()
    } finally {
      if (jpm.currentTransaction.isActive) {
        jpm.currentTransaction.rollback()
      }
      jpm.close()
    }
  }
  
  def close() {
    jpm.close()
  }
  
  def makePersistent[T](dataObj: T): T = { // TODO: can this be PersistenceCapable
    jpm.makePersistent[T](dataObj)
  }
  
  def makePersistentAll[T](dataObjs: Iterable[T]): Iterable[T] = {
    jpm.makePersistentAll[T](dataObjs)
  }
  
  def query[T: ClassManifest](): ScalaQuery[T] = ScalaQuery[T](jpm)
  
  def detachCopy[T: ClassManifest](obj: T): T = jpm.detachCopy(obj)
}

object ScalaPersistenceManager {
  def create(pmf: JDOPersistenceManagerFactory): ScalaPersistenceManager = {
    val spm = new ScalaPersistenceManager(pmf.getPersistenceManager().asInstanceOf[JDOPersistenceManager])
    spm.beginTransaction()
    spm
  }
}

class ScalaQuery[T](val query: TypesafeQuery[T]) {   
  def executeOption(): Option[T] = {
    executeList() match {
      case List(obj) => Some(obj)
      case _ => None
    }
  }
  
  def executeList(): List[T] = {
    import scala.collection.JavaConverters._
    query.executeList().asScala.toList
  }
  
  def filter(expr: BooleanExpression): ScalaQuery[T] = {
    ScalaQuery[T](query.filter(expr))
  }
  
  def orderBy(orderExpr: OrderExpression[_]*): ScalaQuery[T] = {
    ScalaQuery[T](query.orderBy(orderExpr: _*))
  }
}

object ScalaQuery {
  def apply[T: ClassManifest](jpm: JDOPersistenceManager): ScalaQuery[T] = {
    new ScalaQuery[T](jpm.newTypesafeQuery[T](classManifest[T].erasure))
  }
  
  def apply[T](query: TypesafeQuery[T]): ScalaQuery[T] = {
    new ScalaQuery[T](query)
  }
}