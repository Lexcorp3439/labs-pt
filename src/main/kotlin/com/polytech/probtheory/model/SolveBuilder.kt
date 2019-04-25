package com.polytech.probtheory.model

interface SolveBuilder {

    fun read()
    fun buildHypo(): List<Hypothesis>
}