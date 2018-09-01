package com.knoldus.backup.examples

object Example3 extends App {

    def f(value: Int): (Int, String) = {
        (value + 57, s"Execute function f with $value value.")
    }

    def g(value: Int): (Int, String) = {
        (value + 26, s"Execute function g with $value value.")
    }

    val (resultG, logG) = g(3)
    val (resultF, logF) = f(resultG)

    println(s"Result: $resultF")

    val logs =
        s"""Logs ...
          |$logG
          |$logF
        """.stripMargin
    println(logs)
}
