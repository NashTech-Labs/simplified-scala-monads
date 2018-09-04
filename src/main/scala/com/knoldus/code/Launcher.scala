package com.knoldus.code

object Launcher extends App {

    val f: Int => Int = value => {
        println(s"Execute function f with $value value.")
        value + 57
    }

    val g: Int => Int = value => {
        println(s"Execute function g with $value value.")
        value + 26
    }

    val result = (g andThen f)(3)
    println(s"Results $result")
}
