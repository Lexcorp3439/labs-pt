package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Property
import com.polytech.probtheory.model.Solver
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.Styler
import java.lang.StringBuilder
import java.util.*

class Solve(hypo: List<Hypothesis>, exps: List<Experiment>, property: Property) : Solver(hypo, exps, property) {
    val characters = List(24) { Character() }

    var upCase = 0     // Заглавные
    var lowCase = 0     // Строчные
    var voiced = 0     // Звонкие
    var deaf = 0     // Глухие
    var dividing = 0     // Разделительные
    var vowels = 0     // Гласные
    var consonants = 0     // Соглласные

    override fun run() {

        val oldHypo = hypo
        // 1.a
        val allHypos = countPostNew()
        drawHypo(allHypos)

        // 1.b
        var max = 0.0
        var key = CharArray(1)
        for ((k, v) in allHypos) {
            if (v.last() > max) {
                key = k
                max = v.last()
            }
            if (max == 1.0) {
                break
            }
        }
        draw(allHypos[key] ?: error(""), 10, "1.b", Arrays.toString(key))

        // 1.c
        var count = 0
        val counter = mutableListOf<Int>()
        for (i in 0 until (allHypos[key]?.size ?: 0)) {
            for ((k, v) in allHypos) {
                if (v[i] > 0.00001) {
                    count++
                    println("i=$i + ${Arrays.toString(k)}")
                }
            }
            println("\n")
            counter.add(count)
            count = 0
        }
        draw(counter, 10, "1.c")

        // 2.a - b
        val all = mutableListOf<Map<Char, List<Double>>>()
        for (i in 0 until 24) {
            val result = secondPost(createDef(), i + 1)
            all.add(result)
        }
        print2ab(all)
        for (i in 0 until 24) {
            drawSecond(all[i], i + 1)
        }

        // 2.c
        for (c in characters) {
            consonants += if (c.consonants) 1 else 0
            deaf += if (c.deaf) 1 else 0
            dividing += if (c.dividing) 1 else 0
            lowCase += if (c.lowCase) 1 else 0
            upCase += if (c.upCase) 1 else 0
            voiced += if (c.voiced) 1 else 0
            vowels += if (c.vowels) 1 else 0
        }

        for (i in 0 until 24) {
            when {
                characters[i].voiced -> printRes(all[i], voiced)
                characters[i].deaf -> printRes(all[i], deaf)
                characters[i].dividing -> printRes(all[i], dividing)
                characters[i].vowels -> printRes(all[i], vowels)
            }
        }


//        3.a + 3.b
        val psExp = generatePsExp()                         // генерация экспериментального профиля
//        3.c
        val tClasses =
            generatePsTeorClass(characters, oldHypo)        // генерация теоретического профиля с фильтрацией по классам
        val psTeors = generatePSTeor(tClasses)
        println(psExp)
        for (p in psTeors) {
            println(p)
        }
//        3.d in generatePsExp(

    }

    fun printRes(map: Map<Char, List<Double>>, count: Int) {
        var mapS = map.toList().sortedByDescending { (_, list) -> list[(list.size * 0.75).toInt()] }.toMap()
        mapS = mapS.toList().subList(0, count).toMap()
        val strB = StringBuilder()
        strB.append("count = $count")
        for (r in mapS) {
            if (r.value[(r.value.size * 0.95).toInt()] != 0.0) {
                strB.append(" ${r.key} ")
            }

        }
        println(strB.toString())
    }

    fun print2ab(all: List<Map<Char, List<Double>>>) {
        for ((i, a) in all.withIndex()) {
            println(i + 1)
            val map = a.toList().sortedByDescending { (_, list) -> list.last() }.toMap()
            for (r in map) {
                if (r.value[(r.value.size * 0.95).toInt()] != 0.0) {
                    println("char = ${r.key} --- list = ${r.value[(r.value.size * 0.95).toInt()]} \n")
                }
//                if (i == 1) println("char = ${r.key} --- list = ${r.value[r.value.size - 1]} \n")
            }
            println()
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
//            println("$i")
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

    val all = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя-_"

    fun createDef(): List<LeterHypo> {
        val p = 1.0 / all.length

        val defaultH = mutableListOf<LeterHypo>()
        for (c in all) {
            defaultH.add(LeterHypo(p, CharH(c)))
        }
        hypo = defaultH
        return defaultH
    }

    fun secondPost(hypoS: List<Hypothesis>, index: Int): Map<Char, List<Double>> {
        val hypos = hypoS.toMapS()
        val allHypos = mutableMapOf<Char, MutableList<Double>>()

        for (h in hypoS) {
            allHypos[(h.info as CharH).c] = mutableListOf()
            allHypos[h.info.c]?.add(h.p)
        }

        for ((i, exp) in exps.withIndex()) {
//            println("$i")
            val e = exp as Letter
            if (e.e.first > -1) {
                characters[e.e.first - 1].consonants = e.consonants
                characters[e.e.first - 1].deaf = e.deaf
                characters[e.e.first - 1].dividing = e.dividing
                characters[e.e.first - 1].lowCase = e.lowCase
                characters[e.e.first - 1].upCase = e.upCase
                characters[e.e.first - 1].voiced = e.voiced
                characters[e.e.first - 1].vowels = e.vowels
            }
            if (e.e.first > -1 && e.e.first == index || e.e.first < 0) {
                changeAllPAH(exp)
                changePA()
//                if (i < 32) println(pA)
                for (hw in hypos) {
                    if (allHypos.containsKey((hw.value.info as CharH).c)) {
                        when {
                            hw.value.p > 0.0 -> {
                                hw.value.changeP(pA)
                                allHypos[hw.key]?.add(hw.value.p)
                            }
                            else -> allHypos[hw.key]?.add(hw.value.p)
                        }
                    }
                }
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

    fun drawSecond(map: Map<Char, List<Double>>, index: Int) {
        val chart =
            XYChartBuilder().width(800).height(600)
                .theme(Styler.ChartTheme.Matlab)
                .title(index.toString())
                .xAxisTitle("exp").yAxisTitle("p")
                .build()

        val stop = 1000
        val x = List(stop) { ind -> ind }

        val mapS = map.toList().sortedByDescending { (_, list) -> list.last() }.toMap()
        for (m in mapS) {
            if (m.value.last() != 0.0) {
                val y = m.value.subList(0, stop)
                chart.addSeries(m.key.toString(), x, y)
            }
        }

        SwingWrapper(chart).displayChart()
    }

    fun drawExp(ys: List<List<Number>>) {
        for (j in 0..2) {
            val chart =
                XYChartBuilder().width(800).height(600)
                    .theme(Styler.ChartTheme.Matlab)
                    .title("3.d")
                    .xAxisTitle("nExp").yAxisTitle("pExp")
                    .build()

            val chars = mutableListOf(
                'д', 'о', 'м', 'и', 'н', 'к', 'а', 'с', 'я', '_', 'р'
                , 'е', 'п', 'у', 'б', 'л'
            )
            val size = ys.first().size
            val x = List(size) { index -> index }

            when (j) {
                0 -> {
                    for (i in 0..6) {
                        chart.addSeries(chars[i].toString(), x, ys[i])
                    }
                }
                1 -> {
                    for (i in 7..13) {
                        chart.addSeries(chars[i].toString(), x, ys[i])
                    }
                }
                2 -> {
                    for (i in 14..15) {
                        chart.addSeries(chars[i].toString(), x, ys[i])
                    }
                }
            }
            SwingWrapper(chart).displayChart()
        }
    }


    fun List<Hypothesis>.toMapH(): MutableMap<CharArray, Hypothesis> {
        val map = mutableMapOf<CharArray, Hypothesis>()
        for (h in this) {
            map[(h.info as Word).word] = h
        }
        return map
    }

    fun List<Hypothesis>.toMapS(): MutableMap<Char, Hypothesis> {
        val map = mutableMapOf<Char, Hypothesis>()
        for (h in this) {
            map[(h.info as CharH).c] = h
        }
        return map
    }

    val upCaseS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЭЮЯ"       // Заглавные
    val lowCaseS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"    // Строчные
    val voicedS = "бвгджзйлмнр"                           // Звонкие
    val deafS = "кпстфхцш"                                // Глухие
    val dividingS = "ьъ-_"                                // Разделительные
    val vowelsS = "оиаыюяэёуе"                            // Гласные
    val consonantsS = "бвгджзйклмнпрстфхцчшщ"             // Соглласные

    fun generatePsExp(): Map<Char, Double> {
        val psExp = mutableMapOf<Char, Double>()                            // Экспериментальный профиль по символам
        val psExpClass = characters                                         // Экспериментальный профиль по классам
        val drawer = MutableList(16) { mutableListOf<Double>() }
        var countD = 0
        var countO = 0
        var countM = 0
        var countU = 0
        var countN = 0
        var countK = 0
        var countA = 0
        var countC = 0
        var countI = 0
        var count_ = 0
        var countR = 0
        var countE = 0
        var countP = 0
        var countY = 0
        var countB = 0
        var countL = 0

        var i = 1
        for (e in (exps as List<Letter>)) {
            if (e.e.first < 0) {
                when (e.e.second.first().toString()) {
                    "д" -> countD++
                    "о" -> countO++
                    "м" -> countM++
                    "и" -> countU++
                    "н" -> countN++
                    "к" -> countK++
                    "а" -> countA++
                    "с" -> countC++
                    "я" -> countI++
                    "_" -> count_++
                    "р" -> countR++
                    "е" -> countE++
                    "п" -> countP++
                    "у" -> countY++
                    "б" -> countB++
                    "л" -> countL++
                }
            }
            drawer[0].add(countD.toDouble() / i)
            drawer[1].add(countO.toDouble() / i)
            drawer[2].add(countM.toDouble() / i)
            drawer[3].add(countU.toDouble() / i)
            drawer[4].add(countN.toDouble() / i)
            drawer[5].add(countK.toDouble() / i)
            drawer[6].add(countA.toDouble() / i)
            drawer[7].add(countC.toDouble() / i)
            drawer[8].add(countI.toDouble() / i)
            drawer[9].add(count_.toDouble() / i)
            drawer[10].add(countR.toDouble() / i)
            drawer[11].add(countE.toDouble() / i)
            drawer[12].add(countP.toDouble() / i)
            drawer[13].add(countY.toDouble() / i)
            drawer[14].add(countB.toDouble() / i)
            drawer[15].add(countL.toDouble() / i)
            i++
        }
        psExp['д'] = countD.toDouble() / i
        psExp['о'] = countO.toDouble() / i
        psExp['м'] = countM.toDouble() / i
        psExp['и'] = countU.toDouble() / i
        psExp['н'] = countN.toDouble() / i
        psExp['к'] = countK.toDouble() / i
        psExp['a'] = countA.toDouble() / i
        psExp['с'] = countC.toDouble() / i
        psExp['я'] = countI.toDouble() / i
        psExp['_'] = count_.toDouble() / i
        psExp['р'] = countR.toDouble() / i
        psExp['е'] = countE.toDouble() / i
        psExp['п'] = countP.toDouble() / i
        psExp['у'] = countY.toDouble() / i
        psExp['б'] = countB.toDouble() / i
        psExp['л'] = countL.toDouble() / i

        drawExp(drawer)
        return psExp
    }


    // Первоначальная фильтрация с помощью классов букв
    fun generatePsTeorClass(chrcts: List<Character>, oldHypo: List<Hypothesis>): List<List<Character>> {
        val classes = mutableListOf<List<Character>>()
        for (h in oldHypo) {
            val psTeorClass = mutableListOf<Character>()
            val w = (h.info as Word).word
            for (c in w) {
                val ch = Character()
                if (upCaseS.contains(c)) {
                    ch.upCase = true
                }
                if (lowCaseS.contains(c)) {
                    ch.lowCase = true
                }
                if (dividingS.contains(c)) {
                    ch.dividing = true
                }
                if (vowelsS.contains(c.toLowerCase())) {
                    ch.vowels = true
                }
                if (consonantsS.contains(c.toLowerCase())) {
                    ch.consonants = true
                    if (voicedS.contains(c.toLowerCase())) {
                        ch.voiced = true
                    } else {
                        ch.deaf = true
                    }
                }
                ch.word = w.joinToString("")
                psTeorClass.add(ch)
            }
            if (psTeorClass == chrcts) {
                println(psTeorClass)
                classes.add(psTeorClass)
            }
        }
        return classes
    }

    fun generatePSTeor(tClasses: List<List<Character>>): List<Map<Char, Double>> {
        val psTeors = mutableListOf<Map<Char, Double>>()
        for (tc in tClasses) {
            val psTeor = mutableMapOf<Char, Double>()
            val word = tc.first().word
            for (c in word) {
                psTeor[c] = word.count { it == c }.toDouble() / word.length
            }
            psTeors.add(psTeor)
        }
        return psTeors
    }

    class Character() {
        var upCase = false     // Заглавные
        var lowCase = false     // Строчные
        var voiced = false     // Звонкие
        var deaf = false     // Глухие
        var dividing = false     // Разделительные
        var vowels = false     // Гласные
        var consonants = false     // Соглласные

        var word = ""

        override fun equals(other: Any?): Boolean {

            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Character

            if (upCase != other.upCase) return false
            if (lowCase != other.lowCase) return false
            if (voiced != other.voiced) return false
            if (deaf != other.deaf) return false
            if (dividing != other.dividing) return false
            if (vowels != other.vowels) return false
            if (consonants != other.consonants) return false

            return true
        }

        override fun hashCode(): Int {
            var result = upCase.hashCode()
            result = 31 * result + lowCase.hashCode()
            result = 31 * result + voiced.hashCode()
            result = 31 * result + deaf.hashCode()
            result = 31 * result + dividing.hashCode()
            result = 31 * result + vowels.hashCode()
            result = 31 * result + consonants.hashCode()
            return result
        }

        override fun toString(): String {
            return "Character(upCase=$upCase, lowCase=$lowCase, voiced=$voiced, deaf=$deaf, dividing=$dividing, vowels=$vowels, consonants=$consonants, word='$word')"
        }


    }
}