package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info

class LeterHypo(p: Double, info: Info) : Hypothesis(p, info) {
    val let = (info as CharH).c
    private var s = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя- ".length
    private var i = 0

    // лажа
    override fun changePAH(exp: Experiment) {
        val e = exp as Letter
        if (let == 'Д') {
            println(e.toString())
        }
        if (p != 0.0) {
            pAH = when {
                e.e.first < 0 && let.toLowerCase() == e.e.second.first() -> 1.0 / (24 * s)
                e.e.first < 0 && let.toLowerCase() != e.e.second.first() -> 23.0 / (24 * s)
                e.e.first > -1 && e.e.second.contains(let) -> {
                    s = e.e.second.size
                    1.0 / s
                }
                else -> 0.0
            }
        }
        if (let == 'Д' && i in 25 .. 31) {
            println("pAH= $pAH")
        }
        i++
    }
}