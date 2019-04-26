package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment

class Letter(val e: Pair<Int, CharSequence>) : Experiment {

    override fun toString(): String {
        return "Experiment(list=$e)\n"
    }
}
