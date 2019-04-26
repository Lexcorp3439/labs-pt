package com.polytech.probtheory.model

import java.io.PrintWriter
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.Styler


abstract class Solver(
    val hypo: List<Hypothesis>, val exps: List<Experiment>,
    val property: Property = Property("output.txt")
) {

    val out = PrintWriter(property.fileName)
    var pA = 0.0

    abstract fun run()

    open fun countPost(): List<List<Double>> {
//        sout(-1, Type.CONCOLE)
        sout(-1, Type.FILE)
        val allHypos = MutableList(hypo.size) { mutableListOf<Double>()}
        allHypos.putHypos(hypo)

        for ((i, exp) in exps.withIndex()) {
            println("$i")
            changeAllPAH(exp)
            changePA()
            for (h in hypo) {
                h.changeP(pA)
            }

            allHypos.putHypos(hypo)
            sout(i, Type.FILE)
        }

        out.close()
        return allHypos
    }

    open fun changePA() {
        pA = 0.0
        for (h in hypo) {
            pA += h.p * h.pAH
        }
    }

    fun changeAllPAH(exp: Experiment) {
        for (h in hypo) {
            h.changePAH(exp)
        }
    }

    fun sout(i: Int, type: Type) {
        val bulid = StringBuilder()
        bulid.append("$i   \n  ")
        for (h in hypo) {
            bulid.append("$h  ")
        }
        bulid.append("\n")

        when (type) {
            Type.FILE -> out.write(bulid.toString())
            Type.CONCOLE -> println(bulid.toString())
        }
    }

    open fun drawHypo(matrix: List<List<Double>>) {
        val chart =
            XYChartBuilder().width(800).height(600)
                .theme(Styler.ChartTheme.Matlab)
                .title(javaClass.simpleName)
                .xAxisTitle("exp").yAxisTitle("p")
                .build()

        val x = mutableListOf<Int>()

        val stop = if (property.stopDraw > matrix.first().size) {
            matrix.first().size
        } else {
            property.stopDraw
        }

        for (i in 0 until stop) {
            x.add(i)
        }

        for ((i, hypos) in matrix.withIndex()) {
            val y = hypos.subList(0, stop) //.getP()
            chart.addSeries("hypo$i", x, y)
        }

        SwingWrapper(chart).displayChart()
    }

    fun xchartExample(x: List<Int>, vararg y: List<Double>) {
        val chart =
            XYChartBuilder().width(800).height(600)
                .title(javaClass.simpleName)
                .xAxisTitle("exp").yAxisTitle("p")
                .build()

        // Create Chart
        chart.addSeries("hypo1", x, y[0])
        chart.addSeries("hypo2", x, y[1])
        chart.addSeries("hypo3", x, y[2])
        chart.addSeries("hypo4", x, y[3])
        chart.addSeries("hypo5", x, y[4])

        SwingWrapper(chart).displayChart()
    }

    enum class Type {
        FILE, CONCOLE
    }

//    Higher order functions

    fun MutableList<MutableList<Double>>.putHypos(hypos: List<Hypothesis>) {
        for ((i, h) in hypos.withIndex()) {
            this[i].add(h.p)
        }
//        this.add(hypos)
    }

    fun List<Hypothesis>.getP(): List<Double> {
        val list = mutableListOf<Double>()
        for (h in this) {
            list.add(h.p)
        }
        return list
    }
}