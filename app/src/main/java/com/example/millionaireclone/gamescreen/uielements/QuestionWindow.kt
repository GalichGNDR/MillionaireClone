package com.example.millionaireclone.gamescreen.uielements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.millionaireclone.data.Models.Question

@Composable
fun QuestionWindow(
    height: Int,
    question: Question?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 60.dp)
            .background(Color(0xFF001233), RoundedCornerShape(30.dp))
            .border(BorderStroke(5.dp, Color.Cyan), RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(height.dp)
    ) {
        Text(text = question?.text ?: "", fontSize = 20.sp, color = Color.White)
    }
}