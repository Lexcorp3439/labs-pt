package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info

class LeterHypo(p: Double, info: Info) : Hypothesis(p, info) {
    val let = (info as CharH).c


    override fun changePAH(exp: Experiment) {
        val e = exp as Letter
        if (p != 0.0) {
            pAH = when {
                e.e.first < 0 && let == e.e.second.first() -> 1.0
                e.e.second.contains(let) -> 1.0
                else -> 0.0
            }
        }
    }
}