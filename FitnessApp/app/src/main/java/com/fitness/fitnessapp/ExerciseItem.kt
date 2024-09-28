package com.fitness.fitnessapp

import android.graphics.Paint.Align
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import model.Exercise

@Composable
fun ExerciseItem(exercise: Exercise, isSelected : Boolean , onClick:() -> Unit){
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick()  },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            val painter = if (exercise.image!!.isNotEmpty()) {
                rememberAsyncImagePainter(model = exercise.image,
                    imageLoader = LocalContext.current.imageLoader.newBuilder()
                        .allowHardware(false)
                        .build()
                )
            } else {
                painterResource(id = R.drawable.squat)  // Add a placeholder image from resources
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { showDialog = true },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = exercise.name,
                fontSize = 18.sp,
                color = if(isSelected) Color.White else Color.Black,
            )
        }
    }
    if (showDialog) {
        ExerciseDetailsDialog(exercise = exercise, onDismiss = { showDialog = false })
    }

}

@Composable
fun ExerciseDetailsDialog(exercise : Exercise, onDismiss:()-> Unit){
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val painter = rememberAsyncImagePainter(model = exercise.image,
                    imageLoader = LocalContext.current.imageLoader.newBuilder()
                        .allowHardware(false)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Name: ${exercise.name}", fontSize = 20.sp, color = Color.Black)
                Text(text = "Type: ${exercise.type}", fontSize = 16.sp, color = Color.DarkGray)
                Text(text = "Muscle: ${exercise.muscle}", fontSize = 16.sp, color = Color.DarkGray)
                Text(text = "Difficulty: ${exercise.difficulty}", fontSize = 16.sp, color = Color.DarkGray)
                Text(text = "Instructions: ${exercise.instructions}", fontSize = 16.sp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(16.dp))


            }
        }
    }
}

@Composable
fun SelectedExercisesButton(
    selectedExercises : List<Exercise>,
    onRemoveExercise: (Exercise) -> Unit,
    sessionTitle: String,
    onCreateSession: () -> Unit

){
    var showDialog by remember { mutableStateOf(false) }


        FloatingActionButton(
            onClick = {showDialog = true},
            modifier = Modifier

                .padding(
                    bottom = 120.dp, start = 250.dp, end = 16.dp
                ),
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
        ) {
            Text(
                text= "Add ${selectedExercises.size} exercises",
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )

        }


    if (showDialog){
        Dialog(onDismissRequest = {showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(16.dp)
            ) {
                    Column(modifier = Modifier.padding(16.dp) ){
                        Text( text = "Selected Exercises", fontSize = 20.sp)

                        Spacer(modifier = Modifier.height(20.dp))
                        selectedExercises.forEach{
                            exercise ->
                            Row (modifier = Modifier.padding(vertical = 4.dp)){
                                Text(text = exercise.name, fontSize = 16.sp)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Remove",
                                    color = Color.Red,
                                    modifier = Modifier.clickable {
                                        onRemoveExercise(exercise)
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if(sessionTitle.isNotEmpty()&& selectedExercises.isNotEmpty()){
                            Surface (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .clickable { onCreateSession() },
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            ){
                                Text(
                                    text = "Create Session",
                                    color = Color.White,
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }else {
                            Text(
                                text = "Enter a session title and select exercises",
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 16.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
            }
        }
    }
}