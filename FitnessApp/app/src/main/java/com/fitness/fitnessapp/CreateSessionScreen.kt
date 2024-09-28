package com.fitness.fitnessapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import coil.compose.rememberAsyncImagePainter
import model.Exercise
import model.Muscle
import model.SessionRequest
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CreateSessionScreen() {
    var sessionTitle by remember { mutableStateOf("") }

    var selectedImageUrl by remember { mutableStateOf("") }
    var showImagePicker by remember { mutableStateOf(false) }
    var imageUrls by remember { mutableStateOf<List<String>>(emptyList()) }


    var selectedMuscle by remember { mutableStateOf("All") }
    var muscles by remember { mutableStateOf<List<String>>(emptyList()) }

    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    var isLoadingExercises by remember { mutableStateOf(false) }

    var selectedExercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    fun fetchExercises(muscle : String){
        isLoadingExercises = true
        if (muscle == "All") {
            RetrofitClient.apiService.getExercises().enqueue(object : Callback<List<Exercise>> {
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


        }else {

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

        Log.d("Retrofit", "Fetching exercises for muscle: $muscle") // debugging


    }

    fun fetchImages(){
        RetrofitClient.apiService.getImages().enqueue(object  : Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful){
                    imageUrls = response.body() ?: emptyList()
                    Log.d("Retrofit", "Fetched images: $imageUrls")
                } else {
                    Log.e("Retrofit", "Failed to fetch images. Response code: ${response.code()} Response message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e("Retrofit", "Error fetching images: ${t.message}")
            }
        })
    }

    LaunchedEffect(Unit) {
        fetchImages()
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
        fetchExercises("All")
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

    fun createSession(){

        val finalImageUrl = if (selectedImageUrl.isNotEmpty()) selectedImageUrl else 

        val sessionData = SessionRequest(
            sessionTitle = sessionTitle,
            exercises= selectedExercises.map { it.id },
            image_url = selectedImageUrl

        )

        RetrofitClient.apiService.createSession(sessionData).enqueue(object : Callback<Response<Unit>> {
            override fun onResponse(call: Call<Response<Unit>>, response: Response<Response<Unit>>){
                if (response.isSuccessful) {
                    Log.d("Retrofit", "Session created successfully")
                    sessionTitle = ""
                    selectedExercises = emptyList()
                } else {
                    Log.e("Retrofit", "Failed to create session")
                }
            }

            override fun onFailure ( call : Call<Response<Unit>>, t: Throwable){
                Log.e("Retrofit", "Error creating session: ${t.message}")

            }
        })
    }

    Box(modifier = Modifier.fillMaxWidth()) {
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
                    painter = rememberAsyncImagePainter(
                        model = if (selectedImageUrl.isNotEmpty()) selectedImageUrl else "https://media.istockphoto.com/id/1147544807/vector/thumbnail-image-vector-graphic.jpg?s=612x612&w=0&k=20&c=rnCKVbdxqkjlcs3xH87-9gocETqpspHFXu5dIGB4wuM=",
                        placeholder = painterResource(R.drawable.add), // Replace with your local placeholder drawable
                        error = painterResource(R.drawable.add)

                    ),
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
                ImagePickerDialog(onImageSelected = { imageUrl ->
                    selectedImageUrl = imageUrl
                    showImagePicker = false
                },imageUrls = imageUrls,
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
                    contentPadding = PaddingValues(top = 16.dp, bottom = 110.dp)
                ) {
                    items(exercises.size) { index ->
                        val exercise = exercises[index]
                        val isSelected = selectedExercises.contains(exercise)
                        ExerciseItem( exercise = exercise , isSelected = isSelected , onClick = {toggleExerciseSelection(exercise)})
                    }
                }
            }

        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            SelectedExercisesButton(
                selectedExercises = selectedExercises,
                onRemoveExercise = {exercise -> removeExercise(exercise)},
                sessionTitle = sessionTitle,
                onCreateSession = {createSession()},
            )
        }

    }

}

@Composable
fun ImagePickerDialog(
    onImageSelected: (String) -> Unit,
    imageUrls : List<String>,
    onDismiss: () -> Unit ) {

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
                    imageUrls.forEach { imageUrl ->

                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .clickable { onImageSelected(imageUrl) },
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
    val allMuscles = listOf("All") + muscles
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        fun getMuscleImageRes(muscle: String): Int {
            return when (muscle) {
                "All" -> R.drawable.all
                "legs" -> R.drawable.legs
                "arms" -> R.drawable.arms
                "chest" -> R.drawable.chest
                "core" -> R.drawable.core
                "back" -> R.drawable.back
                "shoulders" -> R.drawable.shoulders
                "triceps" -> R.drawable.triceps
                "biceps" -> R.drawable.biceps
                "full body" -> R.drawable.fullbody

                else -> R.drawable.running
            }
        }
        items(allMuscles.size) { index ->
            val muscle = allMuscles[index]

            CategoryItem(
                imageRes = getMuscleImageRes(muscle),
                text = muscle,
                isSelected = muscle == selectedMuscle,
                onClick = { onMuscleSelected(muscle)}
            )
        }
    }
}