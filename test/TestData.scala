import models.auth._
import models.files._
import scalajdo._

object TestData {
  val user1: User = new User("jmiller14", "Jim", "Miller", email="jmiller@nada.com", password="temp123")
  val user2: User = new User("amaniraj14", "Aaditya", "Manirajan", password="temp123")
  val user3: User = new User("cjin14", "Choong Won", "Jin")
  val users = List(user1, user2, user3)

  
  
  def load() {
    val dbFile = new java.io.File("data.h2.db")
    if (dbFile.exists) dbFile.delete()
    DataStore.execute { tpm =>
        for(u <- users) {
          tpm.makePersistent(u)
        }
    }
  }
}