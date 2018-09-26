package com.knoldus.code

import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Launcher extends App {

    case class User(id: UUID, email: String)
    case class Address(id: Int, city: String, country: String)

    def findUser(userId: UUID): Future[User] = {
        // perform async database operations and return user
        Future.successful(User(userId, "harmeet@knoldus.com"))
    }

    def getAddress(user: User): Future[Address] = {
        // perform async database operations and return user address
        Future.successful(Address(13, "Moga", "India"))
    }

    // compose methods
    val resultF = for {
        user        <- findUser(UUID.randomUUID())
        address     <- getAddress(user)
    } yield address

    val result = Await.result(resultF, 1 seconds)
    println(s"Result: $result")
}
