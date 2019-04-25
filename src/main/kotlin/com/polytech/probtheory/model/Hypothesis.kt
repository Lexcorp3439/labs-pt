package com.polytech.probtheory.model

abstract class Hypothesis(var p: Double, val info: Info) {
    open var pAH: Double = 0.0

    abstract fun changeP(pA: Double)
    abstract fun changePAH(exp: Experiment)


    override fun toString(): String {
        return "Hypothesis(p=$p) \n"
    }


}