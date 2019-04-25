package com.polytech.probtheory.labs.white

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.SolveBuilder
import java.io.File
import java.util.*

class WhiteBuilder:SolveBuilder {
    val balls = mutableListOf<Experiment>()
    val hypo = mutableListOf<Hypothesis>()

    override fun read() {
        readFiles()
    }

    override fun buildHypo(): List<Hypothesis> {
        for (i in 0..10) {
            hypo.add(Hypo(1.0/11, Box(i)))
        }
        return hypo
    }

    private fun readFiles() {
        val scn = Scanner(this::class.java.getResourceAsStream("/whiteballs/white_balls.txt"))
        while (scn.hasNextLine()) {
            val str = scn.nextLine().replace(" ", "")
            when (str) {
                "White" -> balls.add(Ball(Ball.Color.WHITE))
                "Black" -> balls.add(Ball(Ball.Color.BLACK))
            }
        }
        println(balls.size)
    }
}