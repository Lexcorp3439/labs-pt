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

    val upCase = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    val lowCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    val voiced = "бвгджзйлмнр"
    val deaf = "кпстфхцш"
    val dividing = "ьъ"
    val vowels = "оиаыюяэёуе"
    val consonants = "бвгджзйклмнпрстфхцчшщ"

    val out = PrintWriter("outout")

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
            val place = readPlace(line[1])
            val chars = readInfo(line)
            list.add(Letter(place to chars))
        }
    }

    private fun readPlace(str: String): Int {
        if (str.matches(Regex("\\d+:"))) {
            val s = str.replace(":", "")
            return s.toInt()
        }
        return -1
    }

    private fun readInfo(line: List<String>): CharSequence {
        var abs = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
        for (i in 2 until line.size) {
            abs = when (line[i]) {
                "заглавная" -> deleteBy(abs, upCase) as String
                "строчная" -> deleteBy(abs, lowCase) as String
                "гласная" -> deleteBy(abs, vowels) as String
                "согласная" -> deleteBy(abs, consonants) as String
                "глухая" -> deleteBy(abs, deaf) as String
                "звонкая" -> deleteBy(abs, voiced) as String
                "разделительная" -> deleteBy(abs, dividing) as String
                else -> line[i]
            }
        }
        out.write("$abs  \n")

        return abs
    }

    private fun deleteBy(cur: CharSequence, del: CharSequence): CharSequence {
        val res = StringBuilder()
        for (c in cur) {
            if (c in del) {
                res.append(c)
            }
        }
        return res.toString()
    }

    fun readFiles() {
        val j = this::class.java
        val fs1 = FastScanner(j.getResourceAsStream("/words\\capitals.txt"))
        val fs2 = FastScanner(j.getResourceAsStream("/words\\cities_rus.txt"))
        val fs3 = FastScanner(j.getResourceAsStream("/words\\countries.txt"))
        val fs4 = FastScanner(j.getResourceAsStream("/words\\names_all.txt"))
        val fs5 = FastScanner(j.getResourceAsStream("/words\\rivers.txt"))
        val fs6 = FastScanner(j.getResourceAsStream("/words\\russian_nouns.txt"))
        readFile(fs1, 207)
        readFile(fs2, 1191)
        readFile(fs3, 231)
        readFile(fs4, 262)
        readFile(fs5, 232)
        readFile(fs6, 51301)
    }

    fun readFile(scanner: FastScanner, rN: Int) {
        for (i in 0 until rN) {
            words.add(Word(scanner.next()))
        }
    }
}