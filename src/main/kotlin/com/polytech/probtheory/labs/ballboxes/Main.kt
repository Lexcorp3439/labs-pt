package com.polytech.probtheory.labs.ballboxes

import com.polytech.probtheory.model.Property

fun main() {
    val property = Property("ballout.txt", 100)
    val builder = BallBoxes()
    builder.read()
    builder.buildHypo()
    val solver = Solver(builder.hypo, builder.list, property)
    solver.run()
}