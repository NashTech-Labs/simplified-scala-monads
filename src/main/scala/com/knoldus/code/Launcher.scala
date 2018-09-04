package com.knoldus.code

object Launcher extends App {
    
    def f(value: Int): Int = {
        println(s"Execute function f with $value value.")
        value + 57
    }

    def g(value: Int): Int = {
        println(s"Execute function g with $value value.")
        value + 26
    }

    val result = f(g(3))
    println(s"Results $result")
}
