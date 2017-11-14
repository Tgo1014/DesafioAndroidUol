package tgo1014.desafioandroid.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import tgo1014.desafioandroid.model.Shot

interface DribbbleApi {

    @Headers("Authorization: Bearer 96c8376d2480b8cda1e64ea0c846f569184a72e4fd0d749ca2a90fa39512ac9a")
    @GET("shots?per_page=15")
    fun getShots(@Query("page") page: Int): Call<List<Shot>>

    @Headers("Authorization: Bearer 96c8376d2480b8cda1e64ea0c846f569184a72e4fd0d749ca2a90fa39512ac9a")
    @GET("shots/{shotId}")
    fun getSingleShot(@Path("shotId") shotId: Int): Call<Shot>
}