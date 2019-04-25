package com.polytech.probtheory.labs.words

import com.polytech.probtheory.utils.FastScanner
import java.io.File
import java.util.*

class Words : Experiments {
    var list = mutableListOf<Experiment>()
    var words = mutableListOf<Word>()

    val upCase = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    val lowCase = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    val voiced = "бвгджзйлмнр"
    val deaf = "кпстфхцш"
    val dividing = "ьъ"
    val vowels = "оиаыюяэёуе"
    val consonants = "бвгджзйклмнпрстфхцчшщ"

    override fun read(scanner: Scanner) {
        readFiles()
        readExp(scanner)
    }

    override fun read(scanner: FastScanner) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun readExp(scn: Scanner) {
        val nExp = 10000
        while (scn.hasNextLine()) {
            val line = scn.nextLine().split(" ")
            val place = readPlace(line[1])
            val chars = readInfo(line)
            list.add(Experiment(place to chars))
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
                "гласная" -> deleteBy(abs, voiced) as String
                "согласная" -> deleteBy(abs, consonants) as String
                "глухая" -> deleteBy(abs, deaf) as String
                "звонкая" -> deleteBy(abs, voiced) as String
                "разделительная" -> deleteBy(abs, dividing) as String
                else -> line[i]
            }
        }
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
        val fs1 = FastScanner(File("L:\\IdeaProjects\\words\\src\\main\\resources\\capitals.txt"))
        val fs2 = FastScanner(File("L:\\IdeaProjects\\words\\src\\main\\resources\\cities_rus.txt"))
        val fs3 = FastScanner(File("L:\\IdeaProjects\\words\\src\\main\\resources\\countries.txt"))
        val fs4 = FastScanner(File("L:\\IdeaProjects\\words\\src\\main\\resources\\names_all.txt"))
        val fs5 = FastScanner(File("L:\\IdeaProjects\\words\\src\\main\\resources\\rivers.txt"))
        val fs6 = FastScanner(File("L:\\IdeaProjects\\words\\src\\main\\resources\\russian_nouns.txt"))
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

    class Experiment(val e: Pair<Int, CharSequence>) {

        override fun toString(): String {
            return "Experiment(list=$e)\n"
        }
    }

    class Word(val word: CharSequence) {
        override fun toString(): String {
            return "Word(word=$word)"
        }
    }
}