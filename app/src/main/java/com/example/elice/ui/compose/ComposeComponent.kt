package com.example.elice.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.elice.ui.compose.theme.Purple80

@Composable
fun SpacerFun(size: Int) {
    Spacer(
        modifier = Modifier
            .height(size.dp)
    )
}

@Composable
fun SimpleCircularProgressComponent(
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),

            color = Purple80,

            strokeWidth = Dp(value = 4F)
        )
    }
}


@Composable
fun Circle(
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(15.dp)
            .drawBehind {
                drawCircle(color = color, style = Stroke(3.dp.toPx()))
            },
    )
}


@Composable
fun CircleWithLine(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .size(15.dp)
            .drawBehind {
                // Draw circle
                drawCircle(color, style = Stroke(3.dp.toPx()), center = center)

                // Draw line below the circle to the height of the Box
                val lineHeight = size.height - 3.dp.toPx() // Subtracting the stroke width
                drawLine(
                    color = color,
                    start = Offset(center.x - 7.5.dp.toPx(), lineHeight),
                    end = Offset(center.x + 7.5.dp.toPx(), lineHeight),
                    strokeWidth = 3.dp.toPx()
                )
            }
    )
}
