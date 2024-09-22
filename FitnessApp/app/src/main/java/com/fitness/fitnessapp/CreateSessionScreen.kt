package com.fitness.fitnessapp

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import model.Exercise
import model.Muscle
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CreateSessionScreen() {
    var sessionTitle by remember { mutableStateOf("") }
    var selectedImagesRes by remember { mutableStateOf(R.drawable.add_image_icon) }
    var showImagePicker by remember { mutableStateOf(false) }

    var selectedMuscle by remember { mutableStateOf("Cardio") }
    var muscles by remember { mutableStateOf<List<String>>(emptyList()) }

    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    var isLoadingExercises by remember { mutableStateOf(false) }

    var selectedExercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    LaunchedEffect(Unit) {
        RetrofitClient.apiService.getMuscles().enqueue(object : Callback<List<Muscle>> {
            override fun onResponse(call: Call<List<Muscle>>, response: Response<List<Muscle>>){
                if (response.isSuccessful){
                    muscles=response.body()?.map { it.muscle } ?: emptyList()
                    Log.d("Retrofit", "Fetched muscles: $muscles")
                }else{
                    Log.e("Retrofit", "Failed to fetch muscles")
                }
            }
            override fun onFailure(call: Call<List<Muscle>>, t: Throwable) {
                Log.e("Retrofit", "Error fetching muscles: ${t.message}")
            }


        })
    }

    fun fetchExercises(muscle : String){
        isLoadingExercises = true
        Log.d("Retrofit", "Fetching exercises for muscle: $muscle") // debugging

        RetrofitClient.apiService.getExercisesForMuscle(muscle).enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response : Response<List<Exercise>>){
                if (response.isSuccessful){
                    exercises = response.body() ?: emptyList()
                    Log.d("Retrofit", "Fetched exercises: $exercises")
                } else {
                    Log.e("Retrofit", "Failed to fetch exercises")
                }
                isLoadingExercises = false
            }
            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                Log.e("Retrofit", "Error fetching exercises: ${t.message}")
                isLoadingExercises = false
            }

        })
    }

    fun toggleExerciseSelection(exercise: Exercise){
        if(selectedExercises.contains(exercise)) {
            selectedExercises = selectedExercises.filter {it != exercise}
        } else {
            selectedExercises = selectedExercises + exercise
        }
    }
    fun removeExercise(exercise : Exercise){
        selectedExercises = selectedExercises.filter { it != exercise}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Create a New Session",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),

        ) {
            Image(
                painter = painterResource(id = selectedImagesRes),
                contentDescription = "Select image",
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .clickable { showImagePicker = true },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = sessionTitle,
                onValueChange = {sessionTitle= it},
                label = { Text("Session title") },
                placeholder = { Text("Enter session title...") },
                singleLine = true ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        if (showImagePicker){
            ImagePickerDialog(onImageSelected = { imageRes ->
                selectedImagesRes = imageRes
                showImagePicker = false
            },
                onDismiss = {showImagePicker = false})

        }
        if(muscles.isEmpty()) {
            Text(text = "Loading muscles...", modifier = Modifier.padding(16.dp))
        } else {

            MuscleFilter(
                muscles = muscles,
                selectedMuscle = selectedMuscle,
                onMuscleSelected = { muscle ->
                    selectedMuscle = muscle
                    fetchExercises(muscle)
                }
            )
        }

        if (isLoadingExercises) {
            Text(text = "Loading exercises...", modifier = Modifier.padding(16.dp))
        } else if (exercises.isEmpty()) {
            Text(text = "No exercises found.", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(exercises.size) { index ->
                    val exercise = exercises[index]
                    val isSelected = selectedExercises.contains(exercise)
                    ExerciseItem( exercise = exercise , isSelected = isSelected , onClick = {toggleExerciseSelection(exercise)})
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        SelectedExercisesButton(
            selectedExercises = selectedExercises,
            onRemoveExercise = {exercise -> removeExercise(exercise)})
    }
}

@Composable
fun ImagePickerDialog(
    onImageSelected: (Int) -> Unit,
    onDismiss: () -> Unit ) {
    val images = listOf(R.drawable.backex,R.drawable.squat)

    Dialog(onDismissRequest = {onDismiss()}) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.White,
            shape = MaterialTheme.shapes.medium
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ){
                Text("Select an Image", fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    images.forEach { imageRes ->

                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .clickable { onImageSelected(imageRes) },
                            contentScale = ContentScale.Crop
                        )

                    }
                }

            }
        }
    }
}



@Composable
fun CategoryItem(imageRes: Int, text: String, isSelected: Boolean, onClick: () -> Unit){
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .height(60.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(30.dp),
        color = backgroundColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(8.dp)
        ){
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun MuscleFilter(
    muscles : List<String>,
    selectedMuscle: String,
    onMuscleSelected: (String) -> Unit
){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(muscles.size) { index ->
            val muscle = muscles[index]
            CategoryItem(
                imageRes = R.drawable.squat,
                text = muscle,
                isSelected = muscle == selectedMuscle,
                onClick = { onMuscleSelected(muscle)}
            )
        }
    }
}