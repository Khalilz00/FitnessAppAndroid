package network

import model.Activity
import retrofit2.Call
import retrofit2.http.GET
import model.Exercise
import model.Muscle
import model.Session
import model.SessionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
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

    @POST("/create-session")
    fun createSession(@Body sessionData: SessionRequest): Call<Response<Unit>>

    @GET("/get-images")
    fun getImages(): Call<List<String>>

    @GET("/get-session/{id}")
    fun getSession(@Path("id") sessionId: String): Call<Session>

    @GET("/get-session-exercises")
    fun getSessionExercises(@Query("sessionId") sessionId: String): Call<List<Exercise>>

    @POST("/save-activity")
    fun saveSessionActivity(@Body activity: Activity): Call<Unit>

}
