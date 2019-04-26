package com.polytech.probtheory.labs.ballboxes

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Property
import com.polytech.probtheory.model.Solver

class Solver(hypo: List<Hypothesis>, exps: List<Experiment>, property: Property): Solver(hypo, exps, property) {
    override fun run() {
        val allHypos = countPost()
        drawHypo(allHypos)
    }

    override fun changePA() {
        pA = 0.0
        for (h in hypo) {
            pA += h.p * h.pAH * 0.9
        }
    }
}