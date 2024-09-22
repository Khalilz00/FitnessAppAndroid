package network

import retrofit2.Call
import retrofit2.http.GET
import model.Exercise
import model.Muscle
import model.Session
import retrofit2.http.Query

interface ApiService {
    @GET("/get-excercises")
    fun getExercises(): Call<List<Exercise>>

    @GET("/get-sessions")
    fun getSessions(): Call<List<Session>>

    @GET ("get-muscles")
    fun getMuscles(): Call<List<Muscle>>

    @GET("/get-exercises-by-muscle")
    fun getExercisesForMuscle(@Query("muscle") muscle: String): Call<List<Exercise>>


}
