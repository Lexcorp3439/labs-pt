package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Property

fun main() {
    val property = Property("wordsout.txt")
    val builder = Words()
    builder.read()
    builder.buildHypo()
    val solve = Solve(builder.hypo, builder.list, property)
    solve.run()
}