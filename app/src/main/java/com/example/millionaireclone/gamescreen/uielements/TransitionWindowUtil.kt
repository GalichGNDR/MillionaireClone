package com.example.millionaireclone.gamescreen.uielements

fun formatTransitionLevelsNumbers(num: Int): String {
    val str = num.toString()
    val numStr = str.mapIndexed { index, c ->
        if (str.length > 3 && index % 3 == 0 && str.lastIndex != index)
            "$c."
        else
            c
    }
    return  numStr.joinToString("")
}