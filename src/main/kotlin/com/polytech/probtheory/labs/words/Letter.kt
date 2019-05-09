package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import java.util.*

class Letter(val e: Pair<Int, CharArray>) : Experiment {
    var upCase     = false     // Заглавные
    var lowCase    = false     // Строчные
    var voiced     = false     // Звонкие
    var deaf       = false     // Глухие
    var dividing   = false     // Разделительные
    var vowels     = false     // Гласные
    var consonants = false     // Соглласные

    override fun toString(): String {
        return "Letter(e=${e.first}  ${Arrays.toString(e.second)}, upCase=$upCase, lowCase=$lowCase, voiced=$voiced, deaf=$deaf, dividing=$dividing, vowels=$vowels, consonants=$consonants)"
    }


}
