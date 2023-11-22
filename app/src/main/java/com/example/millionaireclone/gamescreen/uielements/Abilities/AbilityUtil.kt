package com.example.millionaireclone.gamescreen.uielements.Abilities

import com.example.millionaireclone.R

fun callAnswerTextToPicture(answer: String): Int? {
    return when (answer) {
        "A" -> R.drawable.big_a_white
        "B" -> R.drawable.big_b_white
        "C" -> R.drawable.big_c_white
        "D" -> R.drawable.big_d_white
        else -> null
    }
}