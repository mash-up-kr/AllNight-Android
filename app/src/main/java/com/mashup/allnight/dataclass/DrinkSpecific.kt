package com.mashup.allnight.dataclass

data class DrinkSpecific(
    val TITLE: String,
    val INTRO: String,
    val GLASS: String,
    val NEED: MutableList<String>,
    val INSTRUCTION: String
)