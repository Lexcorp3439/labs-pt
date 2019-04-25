package com.polytech.probtheory.model

import java.io.PrintWriter
import javafx.scene.chart.XYChart
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder




abstract class Solver(val hypo: List<Hypothesis>, val exps: List<Experiment>) {
    open lateinit var out: PrintWriter
    open var pA = 0.0

    abstract fun run()

    fun countPost() {
        out = PrintWriter("output_white.txt")
        sout(-1, Type.CONCOLE)
        sout(-1, Type.FILE)
        val listI = mutableListOf<Int>()
        val hypo1 = mutableListOf<Double>()
        val hypo2 = mutableListOf<Double>()
        val hypo3 = mutableListOf<Double>()
        val hypo4 = mutableListOf<Double>()
        val hypo5 = mutableListOf<Double>()


        for ((i, exp) in exps.withIndex()) {
            listI.add(i)
            hypo1.add(hypo[0].p)
            hypo2.add(hypo[1].p)
            hypo3.add(hypo[2].p)
            hypo4.add(hypo[3].p)
            hypo5.add(hypo[4].p)
            changeAllPAH(exp)
            changePA()
            for (h in hypo) {
                h.changeP(pA)
            }
            sout(i, Type.FILE)
        }

        xchart(listI, hypo1, hypo2, hypo3, hypo4, hypo5)
        out.close()
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

    fun sout(i : Int, type: Type) {
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

    fun xchart(x: List<Int>, vararg y: List<Double>) {
        val chart = XYChartBuilder().width(800).height(600).title(javaClass.simpleName).xAxisTitle("exp").yAxisTitle("p").build()

        // Create Chart
        chart.addSeries("hypo1", x, y[0])
        chart.addSeries("hypo2", x, y[1])
        chart.addSeries("hypo3", x, y[2])
        chart.addSeries("hypo4", x, y[3])
        chart.addSeries("hypo5", x, y[4])

        SwingWrapper(chart).displayChart()
    }

    enum class Type{
        FILE, CONCOLE
    }
}