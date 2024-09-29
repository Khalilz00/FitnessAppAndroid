import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.fitness.fitnessapp.R
import com.fitness.fitnessapp.ui.theme.FitnessAppTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var isComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(4000)
        onTimeout()
    }

    val words = listOf("COMMIT", "TO", "PROGRESS")

    var currentWordIndex by remember { mutableStateOf(0) }

    LaunchedEffect(currentWordIndex) {
        if (currentWordIndex < words.size) {
            delay(1000) // Wait 1 second between each word
            currentWordIndex++
        } else {
            delay(500) // Wait 500ms before fading out
            isComplete = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                words.forEachIndexed { index, word ->
                    if (index <= currentWordIndex) {
                        AnimatedVisibility(
                            visible = !isComplete,
                            enter = fadeIn(animationSpec = tween(1000)),
                            exit = fadeOut(animationSpec = tween(500)),
                        ) {
                            Text(
                                text = word,
                                style = TextStyle(
                                    fontSize = 50.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(5f, 5f),
                                        blurRadius = 8f
                                    )

                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FitnessAppTheme {
        SplashScreen {}
    }
}
