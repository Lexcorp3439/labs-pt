package com.polytech.probtheory.labs.ballboxes

import com.polytech.probtheory.model.Experiment
import com.polytech.probtheory.model.Hypothesis
import com.polytech.probtheory.model.Info
import com.polytech.probtheory.model.SolveBuilder
import com.polytech.probtheory.utils.FastScanner
import java.io.File

class BallBoxes : SolveBuilder {
    var n_boxes = 5
    var m = 6
    var d = 3
    var p_change_box = 0.100
    var nExp = 10000
    var list = mutableListOf<Experiment>()
    var boxes = mutableListOf<Box>()
    val hypo = mutableListOf<Hypothesis>()

    override fun read() {
        val j = this::class.java
        val scanner = FastScanner(j.getResourceAsStream("/ballboxes\\\\task_1_ball_boxes.txt"))
        parseBox(scanner)
        pardeExp(scanner)    }

    override fun buildHypo(): List<Hypothesis> {
        val p = 1.0 * (1 - p_change_box) / n_boxes
        println(p)
        for (box in boxes) {
            hypo.add(Hypo(p, box, 1 - p_change_box))
        }
        return hypo
    }

    fun parseBox(scn: FastScanner) {
        var total = 3
        var red = 3
        var white = 3
        var black = 3
        var green = 3
        var blue = 3
        var yellow = 3
        var i = 0
        var c = 0
        while (i < n_boxes) {
            when (scn.next()) {
                "Total:" -> total = scn.next().replace(".", "").toInt()
                "Red:" -> red = scn.next().replace(",", "").toInt()
                "White:" -> white = scn.next().replace(",", "").toInt()
                "Black:" -> black = scn.next().replace(",", "").toInt()
                "Green:" -> green = scn.next().replace(",", "").toInt()
                "Blue:" -> blue = scn.next().replace(",", "").toInt()
                "Yellow:" -> yellow = scn.nextInt()
                else -> c--
            }
            c++
            if (c == m + 1) {
                boxes.add(Box(total, red, white, black, green, blue, yellow))
                c = 0
                i++
            }
        }
    }

    fun pardeExp(scn: FastScanner) {
        var current = BoxExp()
        var i = 0
        var c = 0
        while (i < nExp) {
            when (scn.next().replace(",", "")) {
                "Red" -> current.list.add(BoxExp.Color.RED)
                "White" -> current.list.add(BoxExp.Color.WHITE)
                "Black" -> current.list.add(BoxExp.Color.BLACK)
                "Green" -> current.list.add(BoxExp.Color.GREEN)
                "Blue" -> current.list.add(BoxExp.Color.BLUE)
                "Yellow" -> current.list.add(BoxExp.Color.YELLOW)
                else -> c--
            }
            c++
            if (c == d) {
                list.add(current)
                current = BoxExp()
                c = 0
                i++
            }
        }
    }
}