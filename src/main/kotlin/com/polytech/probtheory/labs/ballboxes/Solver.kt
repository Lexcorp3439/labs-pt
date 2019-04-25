package com.polytech.probtheory.labs.ballboxes

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Solver

class Solver(hypo: List<Hypothesis>, exps: List<Experiment>): Solver(hypo, exps) {
    override fun run() {
        countPost()
    }

    override fun changePA() {
        pA = 0.0
        for (h in hypo) {
            pA += h.p * h.pAH * 0.9
        }
    }
}