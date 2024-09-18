package com.fitness.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.fitness.fitnessapp.ui.theme.FitnessAppTheme
import kotlinx.coroutines.delay
import okio.Timeout

@Composable
fun SplashScreen(onTimeout : () -> Unit) {
    //Timer 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null ,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            text= "BLA BLA BLA",
            style = TextStyle(fontSize = 32.sp , color= Color.White)
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FitnessAppTheme {
        SplashScreen {}
    }
}