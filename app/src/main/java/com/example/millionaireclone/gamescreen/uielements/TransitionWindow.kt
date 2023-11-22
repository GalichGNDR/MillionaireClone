package com.example.millionaireclone.gamescreen.uielements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Preview
@Composable
fun TransitionWindow(
    currQuestionNumber: MutableStateFlow<Int> = MutableStateFlow(1)
) {
    val levels = listOf(500,1000,2000,3000,5000,10000,15000,25000,50000,100000,200000,400000,800000,1500000,3000000)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                interactionSource = remember{ MutableInteractionSource() },
                indication = null
            ){}
    ) {
        for (i in 14 downTo 0) {

            TransitionWindowLevel(
                number = i,
                levels = levels,
                currQuestionNumber = currQuestionNumber
            )
        }
    }
}

@Composable
fun TransitionWindowLevel(
    number: Int,
    levels: List<Int>,
    currQuestionNumber: MutableStateFlow<Int>
) {
    val currQuestionNumberVal = currQuestionNumber.collectAsState().value

    val borderColor = if (number == currQuestionNumberVal)
        Color(0xfff98e0c)
    else if (number < currQuestionNumberVal)
        Color(0xff2ecd3d)
    else if ( (number+1) % 5 == 0)
        Color(0xfff9d02a)
    else
        Color.Cyan
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (number == currQuestionNumberVal) {
            Box(
                modifier = Modifier
                    .padding(end=5.dp)
                    .background(Color(0xfff98e0c), CircleShape)
                    .size(8.dp)
            )
        }
        Text(
            text = formatTransitionLevelsNumbers(levels[number]),
            color = Color.White,
            modifier = Modifier
                .background(Color(0xFF001233), RoundedCornerShape(30.dp))
                .border(BorderStroke(3.dp, borderColor), RoundedCornerShape(30.dp))
                .padding(top = 10.dp, bottom = 10.dp)
                .width((150 + number * 8).dp),
            textAlign = TextAlign.Center
        )
        if (number == currQuestionNumberVal) {
            Box(
                modifier = Modifier
                    .padding(start=5.dp)
                    .background(Color(0xfff98e0c), CircleShape)
                    .size(8.dp)
            )
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
    )
}
