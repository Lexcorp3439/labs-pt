package com.polytech.probtheory.labs.words

import java.io.File
import java.io.PrintWriter
import java.util.*

class Reader (val file: File, val expir: Experiments, val outFile: File = File("out1.txt")){
    lateinit var inp: Scanner
    lateinit var out: PrintWriter

    fun run(): Experiments {
//        inp = FastScanner(file)
        inp = Scanner(file)
        out = PrintWriter(outFile)

        expir.read(inp)

        out.close()
        return expir
    }

}