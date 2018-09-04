package com.knoldus.code

import java.util.UUID

object Launcher extends App {

    case class User(id: UUID, email: String)
    case class Address(id: Int, city: String, country: String)
    case class Email(to: String, subject: String, body: String)

    def findUser(userId: UUID): User = {
        // perform database operations and return user
        User(userId, "harmeet@knoldus.com")
    }

    def getAddress(user: User): Address = {
        // perform database operations and return user address
        Address(13, "Moga", "India")
    }

    def sendEmail(email: Email) = {
        // send an email using third party service
        println(s"Email sent to ${email.to} ...")
    }

    // Create IO Monad
    class IO[A] private(block: => A) {

        def run = block

        def flatMap[B] (f: A => IO[B]): IO[B] = IO(f(run).run)

        def map[B] (f: A => B): IO[B] = flatMap(a => IO(f(a)))
    }

    object IO {
        def apply[A](block: => A): IO[A] = new IO(block)
    }

    // compose methods
    val result : IO[Unit] = for {
        user        <- IO{ findUser(UUID.randomUUID()) }
        address     <- IO{ getAddress(user) }
        _           <- IO{ sendEmail(Email(user.email, address.city + " News", "Good News")) }
    } yield ()

    println(s"Result V1: $result")
    result.run
}
