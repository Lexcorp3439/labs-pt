package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Property
import com.polytech.probtheory.model.Solver
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.Styler

class Solve(hypo: List<Hypothesis>, exps: List<Experiment>, property: Property) : Solver(hypo, exps, property) {
    override fun run() {
        val allHypos = countPost()
        drawHypo(allHypos)
    }

    val set = mutableSetOf<CharArray>()

    override fun countPost(): List<List<Double>> {
        sout(-1, Type.FILE)
        val allHypos = MutableList(hypo.size) { mutableListOf<Double>() }
        val indexR = mutableListOf<Int>()
        allHypos.putHypos(hypo)

//        for (i in  0 .. 3000) {
//            val exp = exps[i]
        for ((i, exp) in exps.withIndex()) {
            println("$i")
            changeAllPAH(exp)
            changePA()
            for ((index, h) in hypo.withIndex()) {
                h.changeP(pA)
                if (h.p == 0.0) {
                    indexR.add(index)
                    allHypos[index] = mutableListOf()
                }
            }
            allHypos.putHypos(hypo, indexR)
            sout(i, Type.FILE)
            println("size = ${indexR.size}")
        }

        out.close()
        return allHypos
    }

    override fun drawHypo(matrix: List<List<Double>>) {
        val chart =
            XYChartBuilder().width(800).height(600)
                .theme(Styler.ChartTheme.Matlab)
                .title(javaClass.simpleName)
                .xAxisTitle("exp").yAxisTitle("p")
                .build()

        val stop = if (property.stopDraw > matrix.first().size) {
            matrix.first().size
        } else {
            property.stopDraw
        }

        val x = MutableList(stop) { index -> index }

        for ((i, hypos) in matrix.withIndex()) {
            if (hypos.isNotEmpty()) {
                val y = hypos.subList(0, stop) //.getP()
                chart.addSeries("hypo$i", x, y)
            }
        }

        SwingWrapper(chart).displayChart()
    }

    fun MutableList<MutableList<Double>>.putHypos(hypos: List<Hypothesis>, wth: List<Int>) {
        for ((i, h) in hypos.withIndex()) {
            if (i !in wth) {
                this[i].add(h.p)
            }
        }
    }
}