package com.fitness.fitnessapp

import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import kotlinx.coroutines.delay
import model.Activity
import model.ActivitySubmission
import model.Exercise
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun StartSessionScreen(sessionId: String?, sessionName: String , imageUrl: String) {

    val decodedImageUrl = Uri.decode(imageUrl)

    var isOverlayVisible by remember { mutableStateOf(true) }

    var startTime by remember { mutableStateOf(0L) }
    var timerRunning by remember { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(0L) }

    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }

    var sessionComplete by remember { mutableStateOf(false) }

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
    var currentExerciseIndex by remember { mutableStateOf(0) }

    // Fetch exercises for the session
    LaunchedEffect(sessionId) {
        sessionId?.let {
            RetrofitClient.apiService.getSessionExercises(it).enqueue(object :
                Callback<List<Exercise>> {
                override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                    if (response.isSuccessful) {
                        exercises = response.body() ?: emptyList()
                    }
                }
                override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                    // Handle error
                }
            })
        }
    }

    fun onDoneClicked() {
        if (currentExerciseIndex < exercises.size - 1) {
            currentExerciseIndex++
        } else {
            timerRunning = false
            sessionComplete = true
        }
    }





    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
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

            Spacer(modifier = Modifier.height(50.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(exercises) { exercise ->
                    val isCurrent = exercises.indexOf(exercise) == currentExerciseIndex

                   ExerciseElement(exercise = exercise, isCurrent = isCurrent , onDoneClicked = {
                       onDoneClicked()
                   })
                }
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
                        color = Color.DarkGray,
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

        if (sessionComplete) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(32.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bravo! You did it!",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Session Time: $formattedTime",
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    IconButton(
                        onClick = {
                            val currentDate = LocalDateTime.now()

                            val activityData = ActivitySubmission(
                                sessionId = sessionId?.toInt() ?: 0,  // Handle nullability of sessionId
                                duration = elapsedTime.toInt(),
                                notes = "No notes",
                                date = currentDate
                            )

                            RetrofitClient.apiService.saveSessionActivity(activityData).enqueue(object :
                                Callback<Unit> {
                                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                                    if (response.isSuccessful) {
                                        // Log success or display success message
                                        println("Session activity saved successfully")
                                    } else {
                                        // Handle the case when the save fails
                                        println("Failed to save session activity")
                                    }
                                }

                                override fun onFailure(call: Call<Unit>, t: Throwable) {
                                    // Handle network or other failures
                                    println("Error saving session activity: ${t.message}")
                                }
                            })

                            // Reset sessionComplete or navigate to another screen
                            sessionComplete = false
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = "OK",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                }
            }
        }
    }

}



@Composable
fun ExerciseElement(exercise: Exercise, isCurrent: Boolean, onDoneClicked: () -> Unit) {
    val backgroundColor = if (isCurrent) Color.DarkGray else Color.LightGray

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = if (isCurrent) Alignment.CenterHorizontally else Alignment.Start
        ) {
            val painter = if (exercise.image!!.isNotEmpty()) {
                rememberAsyncImagePainter(
                    model = exercise.image,
                    imageLoader = LocalContext.current.imageLoader.newBuilder()
                        .allowHardware(false)
                        .build()
                )
            } else {
                painterResource(id = R.drawable.squat)
            }

            if (isCurrent) {
                // Centered Image
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Centered Name
                Text(
                    text = exercise.name,
                    fontSize = 22.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Instructions aligned to the start
                Text(
                    text = exercise.instructions ?: "No instructions available",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Done button
                IconButton(
                    onClick = { onDoneClicked() },
                    modifier = Modifier
                        .width(70.dp)  // Set the desired width
                        .height(45.dp)  // Set the desired height
                        .clip(RoundedCornerShape(12.dp))  // You can adjust the corner radius as needed
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Done",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 16.sp
                    )
                }

            } else {
                // Compact view for non-current exercises
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = exercise.name,
                        fontSize = 18.sp,
                        color = if (isCurrent) Color.White else Color.Black,
                    )
                }
            }
        }
    }
}
