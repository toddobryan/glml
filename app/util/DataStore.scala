package util

import scala.collection.JavaConverters._
import javax.jdo.JDOHelper
import org.datanucleus.api.jdo.JDOPersistenceManager
import org.datanucleus.query.typesafe.{BooleanExpression, OrderExpression, TypesafeQuery}
import javax.jdo.Transaction
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory
import javax.jdo.PersistenceManagerFactory
import org.datanucleus.api.jdo.query.JDOTypesafeQuery
import javax.jdo.PersistenceManager
import javax.jdo.spi.PersistenceCapable
import org.datanucleus.query.typesafe.Expression
import javax.jdo.Extent
import javax.jdo.Query
import org.datanucleus.query.typesafe.TypesafeSubquery

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
  
  def execute[A](block: (ScalaPersistenceManager => A))(implicit pm: ScalaPersistenceManager): A = {
    if (pm != null) block(pm)
    else DataStore.withTransaction( tpm => block(tpm) )
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
  
  def deletePersistent[T](dataObj: T) {
    jpm.deletePersistent(dataObj)
  }
  
  def deletePersistentAll[T](dataObjs: Seq[T]) {
    jpm.deletePersistentAll(dataObjs.asJava)
  }
  
  def makePersistent[T](dataObj: T): T = { // TODO: can this be PersistenceCapable
    jpm.makePersistent[T](dataObj)
  }
  
  def makePersistentAll[T](dataObjs: Seq[T]): Seq[T] = {
    jpm.makePersistentAll[T](dataObjs.asJava).asScala.toList
  }
  
  def extent[T: ClassManifest](includeSubclasses: Boolean = true): Extent[T] = {
    jpm.getExtent[T](classManifest[T].erasure.asInstanceOf[Class[T]], includeSubclasses)
  }
  
  def newQuery[T](extent: Extent[T]): Query = jpm.newQuery(extent)
  
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
    query.executeList().asScala.toList
  }
  
  def executeResultList[R](distinct: Boolean, expr: Expression[R])(implicit man: Manifest[R]): List[R] = {
    query.executeResultList[R](classManifest[R].erasure.asInstanceOf[Class[R]], distinct, expr).asScala.toList
  }
  
  def executeResultUnique[R](distinct: Boolean, expr: Expression[R])(implicit man: Manifest[R]): R = {
    query.executeResultUnique[R](classManifest[R].erasure.asInstanceOf[Class[R]], distinct, expr)
  }
  
  def filter(expr: BooleanExpression): ScalaQuery[T] = {
    ScalaQuery[T](query.filter(expr))
  }
  
  def orderBy(orderExpr: OrderExpression[_]*): ScalaQuery[T] = {
    ScalaQuery[T](query.orderBy(orderExpr: _*))
  }
  
  def excludeSubclasses(): ScalaQuery[T] = {
    ScalaQuery[T](query.excludeSubclasses())
  }
  
  def includeSubclasses(): ScalaQuery[T] = {
    ScalaQuery[T](query.includeSubclasses())
  }
  
  def subquery[R](alias: String)(implicit man: Manifest[R]): TypesafeSubquery[R] = {
    query.subquery(classManifest[R].erasure.asInstanceOf[Class[R]], alias)
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
