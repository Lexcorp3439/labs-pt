package com.polytech.probtheory.labs.ballboxes

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info

class Hypo(p: Double,info: Info): Hypothesis(p, info) {
//    override var pAH: Double = 0.0
    var box: Box = info as Box
    private var z = 0.0

    constructor(p: Double, info: Info, z: Double) : this(p, info) {
        this.z = z
    }

    override fun changeP(pA: Double) {
        p = p * z * pAH / pA
    }

    override fun changePAH(exp: Experiment) {
        val expr = exp as BoxExp
        var mul = 1.0
        for (e in expr.list) {
            mul *= when (e) {
                BoxExp.Color.RED -> box.red
                BoxExp.Color.WHITE -> box.white
                BoxExp.Color.BLACK -> box.black
                BoxExp.Color.GREEN -> box.green
                BoxExp.Color.BLUE -> box.blue
                BoxExp.Color.YELLOW -> box.yellow
            }
        }
        val size = exp.list.size
        pAH = mul / Math.pow(box.total.toDouble(), exp.list.size.toDouble())           //( (size) * (size - 1) * (size - 2) )
    }

    override fun toString(): String {
        return "Hypothesis(p=$p)"
    }
}