package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Property
import com.polytech.probtheory.model.Solver
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.Styler
import java.util.*

class Solve(hypo: List<Hypothesis>, exps: List<Experiment>, property: Property) : Solver(hypo, exps, property) {
    override fun run() {
//        println(exps.size)
//        println(hypo.size)
//        // 1.a
//        val allHypos = countPostNew()
//        drawHypo(allHypos)
//
//        // 1.b
//        var max = 0.0
//        var key = CharArray(1)
//        for ((k, v) in allHypos) {
//            if (v.last() > max) {
//                key = k
//                max = v.last()
//            }
//            if (max == 1.0) {
//                break
//            }
//        }
//        draw(allHypos[key] ?: error(""), 10, "1.b", Arrays.toString(key))
//
//        // 1.c
//        var count = 0
//        val counter = mutableListOf<Int>()
//        for (i in 0 until (allHypos[key]?.size ?: 0)) {
//            for ((k, v) in allHypos) {
//                if (v[i] > 0.00001) {
//                    count++
//                    println("i=$i + ${Arrays.toString(k)}")
//                }
//            }
//            println("\n")
//            counter.add(count)
//            count = 0
//        }
//        draw(counter, 10, "1.c")


        // 2.a
        val newHypo = secondHypo()
        val result = secondPost(newHypo)
        for (r in result) {
//            println("char = ${r.keys} --- list = ${r.values} \n")
        }

    }


    val set = mutableSetOf<CharArray>()

    fun countPostNew(): Map<CharArray, List<Double>> {
        val hypos = hypo.toMapH()
        val allHypos = mutableMapOf<CharArray, MutableList<Double>>()

        for (h in hypo) {
            allHypos[(h.info as Word).word] = mutableListOf()
            allHypos[h.info.word]?.add(h.p)
        }

        for ((i, exp) in exps.withIndex()) {
            println("$i")
            changeAllPAH(exp)
            changePA()
            for (hw in hypos) {
                if (allHypos.containsKey((hw.value.info as Word).word)) {
                    when {
                        hw.value.p > 0.0 -> {
                            hw.value.changeP(pA)
                            allHypos[hw.key]?.add(hw.value.p)
                        }
                        allHypos.size > 231 -> allHypos.remove(hw.key)
                        else -> allHypos[hw.key]?.add(hw.value.p)
                    }
                }
            }
        }
        return allHypos
    }

    val all = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя- "

    fun secondHypo(): List<List<LeterHypo>> {
        val hypos = mutableListOf<List<LeterHypo>>()
        val defaultH = mutableListOf<LeterHypo>()
        val p = 1.0 / all.length
        for (c in all) {
            println("char = $c -- p=$p \n")
            defaultH.add(LeterHypo(p, CharH(c)))
        }
        hypo = defaultH                         // изменяем изначальный hypo
        for (i in 0..23) {
            hypos.add(defaultH)
        }
        return hypos
    }

    fun secondPost( hypoS: List<List<LeterHypo>>) :List<Map<Char, List<Double>>> {
        val hypos = hypoS
        val allHypos = List(hypoS.size) {index -> hypos[index].toMapS() }

        for ((i, exp) in exps.withIndex()) {
            println("$i")
            changeAllPAH(exp)
            changePA()
            for ((ind, hw) in hypos.withIndex()) {
                for (h in hw) {
                    h.changeP(pA)
                    allHypos[ind][(h.info as CharH).c]?.add(h.p)
                }
//                if (allHypos.containsKey((hw.value.info as Word).word)) {
//                    when {
//                        hw.value.p > 0.0 -> {
//                            hw.value.changeP(pA)
//                            allHypos[hw.key]?.add(hw.value.p)
//                        }
//                        allHypos.size > 231 -> allHypos.remove(hw.key)
//                        else -> allHypos[hw.key]?.add(hw.value.p)
//                    }
//                }
            }
        }
        return allHypos
    }

    fun drawHypo(map: Map<CharArray, List<Double>>) {
        val chart =
            XYChartBuilder().width(800).height(600)
                .theme(Styler.ChartTheme.Matlab)
                .title(javaClass.simpleName)
                .xAxisTitle("exp").yAxisTitle("p")
                .build()

//        val stop = if (property.stopDraw > map.first().size) {
//            map.first().size
//        } else {
//            property.stopDraw
//        }
        val stop = property.stopDraw

        val x = MutableList(stop) { index -> index }

        for (m in map) {
            val y = m.value.subList(0, stop)
            if (m.key.size > 22) {
                println(Arrays.toString(m.key).toString())
            }
            chart.addSeries(Arrays.toString(m.key).toString(), x, y)
        }

        SwingWrapper(chart).displayChart()
    }

    fun List<Hypothesis>.toMapH(): MutableMap<CharArray, Hypothesis> {
        val map = mutableMapOf<CharArray, Hypothesis>()
        for (h in this) {
            map[(h.info as Word).word] = h
        }
        return map
    }

//    fun List<LeterHypo>.toMapS(): Map<Char, LeterHypo> {
//        val map = mutableMapOf<Char, LeterHypo>()
//        for (h in this) {
//            map[(h.info as CharH).c] = h
//        }
//        return map
//    }

    fun List<LeterHypo>.toMapS(): Map<Char, MutableList<Double>> {
        val map = mutableMapOf<Char, MutableList<Double>>()
        for (h in this) {
            val list = mutableListOf<Double>()
            list.add(h.p)
            map[(h.info as CharH).c] = list
        }
        return map
    }
}