package com.polytech.probtheory.labs.words

import java.io.File

fun main() {
    val exp = Words()
    Reader(File("L:\\IdeaProjects\\words\\src\\main\\resources\\task_1_words.txt"), exp).run()
    for ( e in exp.list) {
        println(e)
    }
    for ( e in exp.words) {
        println(e)
    }
    println("Hello, World")
}