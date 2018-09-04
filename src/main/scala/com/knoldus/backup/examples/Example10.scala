package com.knoldus.backup.examples

import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Example10 {

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

    // ------------------------------------------------------------------------

    trait Monad[M[_]] {

        def pure[A] (a: A): M[A]

        def map[A, B] (fa: M[A]) (f: A => B): M[B]

        def flatMap[A, B] (fa: M[A]) (f: A => M[B]): M[B]
    }

    case class OptionT[M[_], A](value: M[Option[A]]) {

        def flatMap[B](f: A => OptionT[M, B])(implicit M: Monad[M]): OptionT[M, B] = {
            OptionT(M.flatMap(value)(opt => opt match {
                case Some(a) => f(a).value
                case None => M.pure(None)
            }))
        }

        def map[B](f: A => B)(implicit M: Monad[M]): OptionT[M, B] = {
            OptionT(M.map(value)(_.map(f)))
        }
    }

    implicit val futureMonad = new Monad[Future] {
        override def pure[A](a: A): Future[A] = Future.successful(a)

        override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)

        override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)
    }

    implicit val listMonad = new Monad[List] {
        override def pure[A](a: A): List[A] = List(a)

        override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)

        override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
    }

    val composition : OptionT[List, Address] = for {
        user        <- OptionT(findUsers)
        address     <- OptionT(getAddressList(user))
    } yield address

    println(s"OptionT Result List: ${ composition.value }")
}
