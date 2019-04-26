package com.polytech.probtheory.labs.white

import com.polytech.probtheory.model.Property

fun main() {
    val property = Property("whiteout.txt")
    val builder = WhiteBuilder()
    builder.read()
    builder.buildHypo()
    val solver = SolverWhite(builder.hypo, builder.balls, property)
    solver.run()
}