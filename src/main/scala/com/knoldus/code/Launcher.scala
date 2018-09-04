package com.knoldus.code

object Launcher extends App {
    
    /**
      * def f(value: Int): (Int, String) = {
      * (value + 57, s"Execute function f with $value value.")
      * }
      *
      * def g(value: Int): (Int, String) = {
      * (value + 26, s"Execute function g with $value value.")
      * }
      *
      * val (resultG, logG) = g(3)
      * val (resultF, logF) = f(resultG)
      */

    case class Writer(value: Int, log: String) {

        def map(f: Int => Int): Writer = {
            Writer(f(value), log)
        }

        def flatMap(f: Int => Writer): Writer = {
            val temp = f(value)
            Writer(temp.value, log + " -- " + temp.log)
        }
    }

    def f(value: Int): Writer = {
        Writer(value + 57, s"Execute function f with $value value.")
    }

    def g(value: Int): Writer = {
        Writer(value + 26, s"Execute function g with $value value.")
    }

    val result1 = for {
        a <- g(3)
        b <- f(a)
    } yield b

    println(s"Result1 $result1")

    val result2 = g(3).flatMap { a =>
        f(a).map(b => b)
    }

    println(s"Result2 $result2")

    assert(result1 == result2)
}
