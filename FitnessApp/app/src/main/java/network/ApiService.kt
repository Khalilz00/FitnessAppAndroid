package network

import retrofit2.Call
import retrofit2.http.GET
import model.Exercise

interface ApiService {
    @GET("/get-excercises")
    fun getExercises(): Call<List<Exercise>>
}
