package com.polytech.probtheory.labs.white

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info

class Hypo(p: Double, info: Info) : Hypothesis(p, info) {
    override fun changeP(pA: Double) {
        p = p * pAH / pA
    }

    override fun changePAH(exp: Experiment) {
        val i = info as Box
        pAH = i.count.toDouble() / 10
    }
}