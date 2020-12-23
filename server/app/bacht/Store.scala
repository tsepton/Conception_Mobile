package bacht

import scala.collection.mutable.Map
import scala.swing._
import components.Card

class BachTStore {

  var store = Map[Int, Card]()

  def tell(id: Int, title: String, body: String): Boolean = {
    if (!store.contains(id)) {
      store += (id -> new Card(id, title, body))
    }
    true // Duh ?
  }

  def get(id: Int): Boolean = {
    if (store.contains(id)) {
      store -= id
      true
    } else false
  }

  def ask(id: Int): Boolean = {
    store.contains(id)
  }

  def nask(id: Int): Boolean = {
    !store.contains(id)
  }

  // FIXME : Won't be worked on, we'll use toString
  def print_store {
    print("{ ")
    for ((t, d) <- store)
      print(t + "(" + store(t) + ")")
    println(" }")
  }

  def clear_store {
    store = Map[Int, Card]()
  }

  override def toString(): String = {
    (for ((key, value) <- store) yield s"${key} : ${value}").mkString("\n")
  }

  def getValues {
    (for ((key, value) <- store) yield value).toList
  }
}

object bb extends BachTStore {

  def reset { clear_store } // Duh ?

}
