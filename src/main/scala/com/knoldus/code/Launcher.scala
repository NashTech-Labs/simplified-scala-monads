package com.knoldus.code

import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Launcher extends App {

    case class User(id: UUID, email: String)
    case class Address(id: Int, city: String, country: String)

    def findUser(userId: UUID): Future[Option[User]] = {
        // perform async database operations and return user
        Future.successful(Some(User(userId, "harmeet@knoldus.com")))
    }

    def getAddress(user: User): Future[Option[Address]] = {
        // perform async database operations and return user address
        Future.successful(Some(Address(13, "Moga", "India")))
    }

    /**
      * // compose methods
      * val resultF : Future[Unit] = for {
      * user        <- findUser(UUID.randomUUID())
      * address     <- getAddress(user)
      * _           <- sendEmail(Email(user.email, address.city + " News", "Good News"))
      * } yield ()
      */

    val resultF = for {
        userOption      <- findUser(UUID.randomUUID())
        addressOption   <- userOption match {
            case Some(user) => getAddress(user)
            case None => Future.successful(None)
        }
    } yield addressOption

    val result = Await.result(resultF, 1 seconds)
    println(s"Result: $result")
}
