package com.polytech.probtheory.model

abstract class Hypothesis(open var p: Double, open val info: Info) {
    open var pAH: Double = 0.0

    abstract fun changeP(pA: Double)
    abstract fun changePAH(exp: Experiment)
}