package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import java.util.*

class Letter(val e: Pair<Int, CharArray>) : Experiment {

    override fun toString(): String {
        return "Experiment(list=${e.first} , ${Arrays.toString(e.second)})\n"
    }
}
