package com.polytech.probtheory.labs.words

import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info
import com.polytech.probtheory.model.SolveBuilder
import com.polytech.probtheory.utils.FastScanner
import java.io.File
import java.io.PrintWriter
import java.util.*

class Words : SolveBuilder {
    val list = mutableListOf<Letter>()
    val words = mutableListOf<Word>()
    val hypo = mutableListOf<Hypothesis>()

    val upCase = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"     // Заглавные
    val lowCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"    // Строчные
    val voiced = "бвгджзйлмнр"                           // Звонкие
    val deaf = "кпстфхцш"                                // Глухие
    val dividing = "ьъ-_"                                // Разделительные
    val vowels = "оиаыюяэёуе"                            // Гласные
    val consonants = "бвгджзйклмнпрстфхцчшщ"             // Соглласные

    val out = PrintWriter("outout.txt")

    override fun read() {
        val scn = Scanner(this::class.java.getResourceAsStream("/words/task_1_words.txt"))

        readFiles()
        readExp(scn)

        out.close()
    }

    override fun buildHypo(): List<Hypothesis> {
        val p = 1.0 / words.size
        for (word in words) {
            hypo.add(Hypo(p, word))
        }
        return hypo
    }


    fun readExp(scn: Scanner) {
        val nExp = 10000
        while (scn.hasNextLine()) {
            val line = scn.nextLine().split(" ")
            val place = readPlace(line[2])
            val chars = readInfo(line)
            val let = Letter(place to chars)
            if (line.size > 3) {
                readType(line, let)
            }
            out.write("$let  \n")
            list.add(let)
        }
    }

    private fun readPlace(str: String): Int {
        if (str.matches(Regex("\\d+:"))) {
            val s = str.replace(":", "")
            return s.toInt()
        }
        return -1
    }

    private fun readInfo(line: List<String>): CharArray {
        var abs = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЭЮЯ-_".toCharArray()
        if (line.size == 3) {
            abs = line.last().replace("\"", "").toCharArray()
        } else {
            for (i in 3 until line.size) {
                abs = when (line[i]) {
                    "заглавная" -> deleteBy(abs, upCase, true)
                    "строчная" -> deleteBy(abs, lowCase, true)
                    "гласная" -> deleteBy(abs, vowels)
                    "согласная" -> deleteBy(abs, consonants)
                    "глухая" -> deleteBy(abs, deaf)
                    "звонкая" -> deleteBy(abs, voiced)
                    "разделительная" -> deleteBy(abs, dividing)
                    else -> {
                        line[i].toCharArray()
                    }
                }
            }
        }
        return abs
    }

    private fun readType(line: List<String>, let: Letter) {
        for (i in 3 until line.size) {
            when (line[i]) {
                "заглавная" -> let.upCase = true
                "строчная" -> let.lowCase = true
                "гласная" -> let.vowels = true
                "согласная" -> let.consonants = true
                "глухая" -> let.deaf = true
                "звонкая" -> let.voiced = true
                "разделительная" -> let.dividing = true
            }
        }
    }

    private fun deleteBy(cur: CharArray, del: CharSequence, up: Boolean = false): CharArray {
        val res = StringBuilder()
        for (c in cur) {
            val cc = if (up) c else c.toLowerCase()
            if (cc in del) {
                res.append(c)
            }
        }
        return res.toString().toCharArray()
    }

    fun readFiles() {
        val j = this::class.java
//        val fs1 = FastScanner(j.getResourceAsStream("/words\\capitals.txt"))
//        val fs2 = FastScanner(j.getResourceAsStream("/words\\cities_rus.txt"))
        val fs3 = Scanner(j.getResourceAsStream("/words\\countries.txt"))
//        val fs4 = FastScanner(j.getResourceAsStream("/words\\names_all.txt"))
//        val fs5 = FastScanner(j.getResourceAsStream("/words\\rivers.txt"))
//        val fs6 = FastScanner(j.getResourceAsStream("/words\\russian_nouns.txt"))
//        readFile(fs1, 207)
//        readFile(fs2, 1191)
        readFile(fs3, 231)
//        readFile(fs4, 262)
//        readFile(fs5, 232)
//        readFile(fs6, 51301)
    }

    fun readFile(scanner: Scanner, rN: Int) {
        for (i in 0 until rN) {
            words.add(Word(scanner.nextLine().replace(" ", "_").toCharArray()))
        }
    }
}