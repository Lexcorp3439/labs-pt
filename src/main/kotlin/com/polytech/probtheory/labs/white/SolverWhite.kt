package com.polytech.probtheory.labs.white

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Solver

class SolverWhite(hypo: List<Hypothesis>, exps: List<Experiment>) : Solver(hypo, exps) {
    override fun run() {
        countPost()
    }
}