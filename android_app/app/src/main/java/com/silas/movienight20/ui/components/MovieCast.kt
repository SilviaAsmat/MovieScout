package com.silas.movienight20.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.silas.movienight20.domain.Cast

@Composable
fun MovieCast(
    cast: List<Cast>,
    modifier: Modifier = Modifier,
    onClickCastPhoto: (Int) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier = modifier
            .heightIn(max = 260.dp)
            .padding(16.dp, 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = cast) {
            Column(
                modifier = modifier
                    .background(Color.White)
                    .height(280.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data("http://image.tmdb.org/t/p/" + "w1280" + it.picturePath).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onClickCastPhoto(it.castId) },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = it.name.toString(),
                    maxLines = 1,
                    modifier = Modifier
                        .padding(2.dp, 0.dp)
                        .width(100.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = it.character.toString(),
                    maxLines = 2,
                    modifier = Modifier
                        .padding(2.dp, 0.dp)
                        .width(100.dp),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,

                    )
            }
        }
    }
}