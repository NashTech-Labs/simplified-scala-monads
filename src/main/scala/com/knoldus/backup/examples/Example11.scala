package com.knoldus.backup.examples

import java.util.UUID
import cats.data.OptionT
import cats.instances.list._

object Example11 {

    case class User(id: UUID, email: String)
    case class Address(id: Int, city: String, country: String)
    case class Company(name: String)

    def findUsers: List[Option[User]] = {
        // perform database operations and return user
        List(Some(User(UUID.randomUUID(), "harmeet@knoldus.com")))
    }

    def getAddressList(user: User): List[Address] = {
        // perform database operations and return user address
        List(Address(13, "Moga", "India"))
    }

    def getCompany(user: User): Option[Company] = {
        // perform database operations
        Some(Company("Knoldus"))
    }

    val result : OptionT[List, (User, Address, Company)] = for {
        user        <- OptionT(findUsers)
        addresses   <- OptionT.liftF(getAddressList(user))
        company     <- OptionT.fromOption(getCompany(user))
    } yield (user, addresses, company)

    println(s"OptionT Result List: ${ result.value }")

}
