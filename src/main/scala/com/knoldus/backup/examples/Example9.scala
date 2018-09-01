package com.knoldus.backup.examples

import java.util.UUID

object Example9 extends App {

    case class User(id: UUID, email: String)
    case class Address(id: Int, city: String, country: String)

    def findUsers: List[Option[User]] = {
        // perform async database operations and return user
        List(Some(User(UUID.randomUUID(), "harmeet@knoldus.com")))
    }

    def getAddress(user: User): List[Option[Address]] = {
        // perform async database operations and return user address
        List(Some(Address(13, "Moga", "India")))
    }

    val result = for {
        userOption      <- findUsers
        addressOption   <- userOption match {
                                case Some(user) => getAddress(user)
                                case None => List(None)
                            }
    } yield addressOption

    println(s"Result: $result")
}
