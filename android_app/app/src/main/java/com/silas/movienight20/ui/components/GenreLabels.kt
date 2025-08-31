package com.silas.movienight20.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.silas.movienight20.domain.Genre

@Composable
fun GenreLabels(genres: List<Genre>, modifier: Modifier = Modifier) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = modifier
            .heightIn(max = 50.dp)
            .padding(16.dp, 12.dp, 16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(items = genres) {
            Text(
                text = it.title.toString(),
                fontSize = 16.sp,
                modifier = modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color("#e46827".toColorInt()))
                    .padding(10.dp, 4.dp),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
