package com.polytech.probtheory.labs.ballboxes

fun main() {
    val builder = BallBoxes()
    builder.read()
    val hypos = builder.buildHypo()
    val solver = Solver(hypos, builder.list)
    solver.run()
}