package scalajdo

import scala.collection.JavaConverters._
import javax.jdo.{Extent, Query}
import org.datanucleus.api.jdo.JDOPersistenceManager
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory
import scala.reflect.ClassTag

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
  
  def extent[T](includeSubclasses: Boolean = true)(implicit tag: ClassTag[T]): Extent[T] = {
    jpm.getExtent[T](tag.runtimeClass.asInstanceOf[Class[T]], includeSubclasses)
  }
  
  def newQuery[T](extent: Extent[T]): Query = jpm.newQuery(extent)
  
  def query[T: ClassTag](): ScalaQuery[T] = ScalaQuery[T](jpm)
  
  def detachCopy[T: ClassTag](obj: T): T = jpm.detachCopy(obj)
}

object ScalaPersistenceManager {
  def create(pmf: JDOPersistenceManagerFactory): ScalaPersistenceManager = {
    val spm = new ScalaPersistenceManager(pmf.getPersistenceManager().asInstanceOf[JDOPersistenceManager])
    spm.beginTransaction()
    spm
  }
}
