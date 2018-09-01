package com.knoldus.backup.examples

import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Example10 extends App {

    case class User(id: UUID, email: String)
    case class Address(id: Int, city: String, country: String)

    def findUsers: List[Option[User]] = {
        // perform async database operations and return user
        List(Some(User(UUID.randomUUID(), "harmeet@knoldus.com")))
    }

    def getAddressList(user: User): List[Option[Address]] = {
        // perform async database operations and return user address
        List(Some(Address(13, "Moga", "India")))
    }

    val resultList = for {
        userOption      <- findUsers
        addressOption   <- userOption match {
            case Some(user) => getAddressList(user)
            case None => List(None)
        }
    } yield addressOption

    println(s"Result List: $resultList")

    // ---------------------------------------------------------------

    def findUser(userId: UUID): Future[Option[User]] = {
        // perform async database operations and return user
        Future.successful(Some(User(userId, "harmeet@knoldus.com")))
    }

    def getAddressFuture(user: User): Future[Option[Address]] = {
        // perform async database operations and return user address
        Future.successful(Some(Address(13, "Moga", "India")))
    }

    val resultF = for {
        userOption      <- findUser(UUID.randomUUID())
        addressOption   <- userOption match {
            case Some(user) => getAddressFuture(user)
            case None => Future.successful(None)
        }
    } yield addressOption

    val resultFuture = Await.result(resultF, 1 seconds)
    println(s"Result Future: $resultFuture")
}
