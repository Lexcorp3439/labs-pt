package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Property
import com.polytech.probtheory.model.Solver

class Solve(hypo: List<Hypothesis>, exps: List<Experiment>, property: Property) : Solver(hypo, exps, property) {
    override fun run() {
        val allHypos = countPost()
        drawHypo(allHypos)
    }

    val set = mutableSetOf<CharArray>()

    override fun countPost(): List<List<Double>> {
//        sout(-1, Type.FILE)
        val allHypos = MutableList(hypo.size) { mutableListOf<Double>()}
        allHypos.putHypos(hypo)


        out.close()
        return allHypos    }
}