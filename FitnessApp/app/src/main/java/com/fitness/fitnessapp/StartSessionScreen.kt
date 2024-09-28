package com.fitness.fitnessapp

import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import java.util.Locale


@Composable
fun StartSessionScreen(sessionId: String?, sessionName: String , imageUrl: String) {

    val decodedImageUrl = Uri.decode(imageUrl)

    var isOverlayVisible by remember { mutableStateOf(true) }

    var startTime by remember { mutableStateOf(0L) }
    var timerRunning by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(0L) }

    LaunchedEffect(timerRunning) {
        if(timerRunning)  {
            startTime = System.currentTimeMillis()
            while (timerRunning) {
                currentTime = System.currentTimeMillis()
                delay(1000L)

            }
            }
    }

    val elapsedTime = (currentTime - startTime) / 1000

    val hours = (elapsedTime / 3600)
    val minutes = (elapsedTime % 3600) / 60
    val seconds = (elapsedTime % 60)

    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = rememberAsyncImagePainter(model = decodedImageUrl),
                    contentDescription = "Session Image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop

                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Text(
                    text = sessionName,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),  // Take up remaining space
                    textAlign = TextAlign.Start
                )
                Icon(
                    painter = painterResource(id = R.drawable.timericon), // Replace with your desired icon resource
                    contentDescription = "timer",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display the elapsed time
                Text(
                    text = formattedTime,
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

            }
        }


        if (isOverlayVisible){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.9f))  // Semi-transparent background
                    .align(Alignment.Center)
            ){
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Start workout",
                        color = Color.White,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button to dismiss the overlay
                    IconButton(
                        onClick = { isOverlayVisible = false
                            timerRunning = true},
                        modifier = Modifier
                            .padding(16.dp)
                            .size(100.dp)  // Adjust size as needed
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.play),  // Replace with your PNG resource
                            contentDescription = "Start workout",
                            contentScale = ContentScale.Fit,  // Ensure the image fits within the size
                            modifier = Modifier.size(100.dp)  // Adjust the size of the image
                        )
                    }
                }
            }
        }
    }

}