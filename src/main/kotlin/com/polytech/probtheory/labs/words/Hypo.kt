package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info

class Hypo(p: Double, info: Info) : Hypothesis(p, info) {
    private val word = (info as Word).word

    override fun changeP(pA: Double) {
        if (p != 0.0 && p != 1.0) {
            super.changeP(pA)
        }
    }

    override fun changePAH(exp: Experiment) {
        val e = exp as Letter
        if (p != 0.0) {
            pAH = when {
                e.e.first < 0 -> {
                    word.count(e.e.second.first()).toDouble() / word.size
                }
                e.e.first - 1 < word.size &&
                        e.e.second.contains(word[e.e.first - 1]) -> 1.0
                else -> 0.0
            }
        }
    }

    fun CharArray.count(c: Char): Int {
        var count = 0
        for (ch in this) {
            if (c == ch) {
                count++
            }
        }
        return count
    }
}