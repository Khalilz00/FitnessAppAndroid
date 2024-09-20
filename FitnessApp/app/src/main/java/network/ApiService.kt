package network

import retrofit2.Call
import retrofit2.http.GET
import model.Exercise
import model.Session

interface ApiService {
    @GET("/get-excercises")
    fun getExercises(): Call<List<Exercise>>

    @GET("/get-sessions")
    fun getSessions(): Call<List<Session>>
}
