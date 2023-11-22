package com.example.millionaireclone.gamescreen.uielements.Abilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.millionaireclone.R
import kotlinx.coroutines.delay

@Composable
fun CallAbilityWindow(
    answerOption: String,
    onClose: () -> Unit
) {
    val answerText = remember {
        mutableStateOf("Вызов...")
    }

    LaunchedEffect(answerOption) {
        delay(2000)
        answerText.value = answerOption
    }

    AnimatedVisibility(true) {
        CallAbilityWindowContent(
            answerText = answerText.value,
            onClose = onClose
        )
    }
}

@Composable
fun CallAbilityWindowContent(
    answerText: String,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClose() }
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(start = 30.dp, end = 30.dp, bottom = 80.dp),

        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF001233), RoundedCornerShape(20.dp))
                .border(BorderStroke(5.dp, Color.Cyan), RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .height(350.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.specialist_call_ability),
                contentDescription = null
            )
            Text(
                text = if (answerText == "Вызов...")
                    answerText
                else
                    "Я думаю, правильный ответ: $answerText",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "Чем сложнее вопрос, тем вероятней, что специалист даст неверный ответ",
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "Нажмите в любом месте чтобы закрыть окно",
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
        )
    }
}