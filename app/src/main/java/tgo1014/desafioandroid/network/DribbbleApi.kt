package tgo1014.desafioandroid.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import tgo1014.desafioandroid.model.objects.Shot

interface DribbbleApi {

    @Headers("Authorization: Bearer ${RestClient.apiKey}")
    @GET("shots?per_page=30")
    fun getShots(@Query("page") page: Int, @Query("sort") order: String): Call<List<Shot>>

    @Headers("Authorization: Bearer ${RestClient.apiKey}")
    @GET("shots/{shotId}")
    fun getSingleShot(@Path("shotId") shotId: Int): Call<Shot>
}