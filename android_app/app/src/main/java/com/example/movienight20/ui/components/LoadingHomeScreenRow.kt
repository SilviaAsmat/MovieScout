package com.example.movienight20.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun LoadingRecentlyViewedRow() {
    Row(
        modifier = Modifier
            .heightIn(max = 250.dp)
            .padding(start = 12.dp, end = 0.dp, bottom = 0.dp, top = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
                    .background(Color.LightGray)
                    .height(220.dp)
                    .width(140.dp)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
                    .background(Color.LightGray)
                    .height(220.dp)
                    .width(140.dp)

            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .shimmer()
                    .background(Color.LightGray)
                    .height(220.dp)
                    .width(140.dp)
            )
    }
}