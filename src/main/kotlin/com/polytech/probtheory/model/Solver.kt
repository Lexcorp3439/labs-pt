package com.polytech.probtheory.model

import java.io.PrintWriter

abstract class Solver(val hypo: List<Hypothesis>, val exps: List<Experiment>) {
    open lateinit var out: PrintWriter
    open var pA = 0.0

    abstract fun run()

    fun countPost() {
        out = PrintWriter("output.txt")
        sout(-1, Type.CONCOLE)
        sout(-1, Type.FILE)

        for ((i, exp) in exps.withIndex()) {
            changeAllPAH(exp)
            changePA()
            for (h in hypo) {
                h.changeP(pA)
            }
            sout(i, Type.FILE)
        }

        out.close()
    }

    open fun changePA() {
        pA = 0.0
        for (h in hypo) {
            pA += h.p * h.pAH * 0.9
        }
    }

    fun changeAllPAH(exp: Experiment) {
        for (h in hypo) {
            h.changePAH(exp)
        }
    }

    fun sout(i : Int, type: Type) {
        val bulid = StringBuilder()
        bulid.append("$i   ")
        for (h in hypo) {
            bulid.append("$h  ")
        }
        bulid.append("\n")

        when (type) {
            Type.FILE -> out.write(bulid.toString())
            Type.CONCOLE -> println(bulid.toString())

        }
    }

    enum class Type{
        FILE, CONCOLE
    }
}