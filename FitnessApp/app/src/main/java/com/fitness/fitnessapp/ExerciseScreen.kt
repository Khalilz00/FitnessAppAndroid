package com.fitness.fitnessapp

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fitness.fitnessapp.ui.theme.FitnessAppTheme
import model.Exercise
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ExerciseScreen(modifier: Modifier = Modifier) {
    // Utiliser un état mutable pour stocker la liste des exercices
    var exercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }

    // Appeler Retrofit pour récupérer les exercices
    LaunchedEffect(Unit) {
        RetrofitClient.instance.getExercises().enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        exercises = body
                        Log.d("Retrofit", "Exercices récupérés: $body")
                    }else {
                        Log.d("Retrofit", "Corps de la réponse est nul")
                    }
                } else {
                    Log.d("Retrofit", "Réponse non réussie, code: ${response.code()}")
                }
            }


            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                Log.e("Retrofit", "Erreur lors de l'appel API : ${t.message}")
            }
        })
    }

    // Afficher les exercices récupérés dans l'interface
    if (exercises.isNotEmpty()) {
        // Si la liste des exercices n'est pas vide, on affiche les détails
        for (exercise in exercises) {
            Text(text = "Exercice : ${exercise.name} - ${exercise.muscle}")
        }
    } else {
        // Si la liste est vide, on affiche un message par défaut
        Text(text = "Aucun exercice trouvé.")
    }
}



@Preview(showBackground = true)
@Composable
fun ExerciseScreenPreview() {
    FitnessAppTheme {
        ExerciseScreen()
    }
}