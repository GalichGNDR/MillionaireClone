package com.example.millionaireclone.gamescreen.uielements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopGameBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val buttonsWidth = 140
        val buttonsHeight = 50
        TopBarButton(width = buttonsWidth, height = buttonsHeight,borderColor = Color.Cyan, offsetX = -20, onClicked = {}) {
            Text(text = "Меню", fontSize = 20.sp)
        }
        Spacer(Modifier.weight(1f))
        TopBarButton(width = buttonsWidth-40, height = buttonsHeight,borderColor = Color(0xFFff8e01), onClicked = {}) {
            Text(text = "1/15", fontSize = 20.sp)
        }
        Spacer(Modifier.weight(1f))

        TopBarButton(width = buttonsWidth, height = buttonsHeight,borderColor = Color.Cyan, offsetX = 20, onClicked = {}) {
            Text(text = "500$", fontSize = 20.sp)
        }
    }
}

@Composable
fun TopBarButton(
    width: Int,
    height: Int,
    borderColor: Color,
    offsetX: Int = 0,
    onClicked: () -> Unit,
    textContent: @Composable () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = { /*TODO*/ },
        modifier = Modifier
            .offset(x = offsetX.dp)
            .background(Color(0xFF001233), CircleShape)
            .border(BorderStroke(5.dp, borderColor), CircleShape)
            .size(width.dp, height.dp)
    ) {
        textContent()
    }
}