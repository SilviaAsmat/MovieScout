package com.example.movienight20.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun MovieRibbonOverlay (title: String) {
    val notchDepthDp = 6.dp
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .padding(top = 145.dp)
            .offset(x = 12.dp)
            .wrapContentWidth()
            .drawBehind {
                val notchDepthPx = with(density) { notchDepthDp.toPx() }
                val ribbonPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width - notchDepthPx, size.height / 2f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(ribbonPath, color = Color.Black.copy(alpha = 0.5f))
            }
            .padding(start = 10.dp, end = 15.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 2f
                )
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}