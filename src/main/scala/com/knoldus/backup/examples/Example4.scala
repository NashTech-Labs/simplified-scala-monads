package com.knoldus.backup.examples

object Example4 extends App {

    val result1 = for {
        a <- 1 to 5
        b <- 1 to 5
    } yield (a + b)

    println(result1)

    val result2 = 1 to 5 flatMap { a =>
        1 to 5 map(b => a + b)
    }

    println(result2)

    assert(result1 == result2)
}
