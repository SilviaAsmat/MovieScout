package com.silas.movienight20.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silas.movienight20.R

@Composable
fun SeeMoreButton(onSeeMoreClicked:() -> Unit){
    Text(
        text = stringResource(R.string.see_more_button),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(top = 0.dp, bottom = 6.dp, start = 16.dp)
            .clickable { onSeeMoreClicked() },
        color = Color.Black,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    )
}
