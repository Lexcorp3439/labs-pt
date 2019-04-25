package com.polytech.probtheory.labs.white

fun main() {
    val builder = WhiteBuilder()
    builder.read()
    val hypos = builder.buildHypo()
    val solver = SolverWhite(hypos, builder.balls)
    solver.run()
}