package com.edulearn.app.utility

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String){
    TopAppBar(title = { Text(title, fontWeight = FontWeight.Bold) }, colors =
    TopAppBarColors(
        Color(0xFFFFA726),
        Color(0xFFFFA726),
        Color.Blue,
        (Color.White),
        Color(0xFFFFA726)
    ),
        navigationIcon = {
            Box(modifier = Modifier) {
                Modifier
                    .align(Alignment.TopStart)
            }
        }
    )
}