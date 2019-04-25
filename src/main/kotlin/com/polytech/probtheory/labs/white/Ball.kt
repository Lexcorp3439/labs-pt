package com.polytech.probtheory.labs.white

import com.polytech.probtheory.model.Experiment

class Ball(val color: Color): Experiment {
    enum class Color{
        WHITE, BLACK
    }
}