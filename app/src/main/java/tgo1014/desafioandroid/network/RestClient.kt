package tgo1014.desafioandroid.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tgo1014.desafioandroid.BuildConfig
import tgo1014.desafioandroid.model.Shot

object RestClient {

    private val DRIBBBLE_API: DribbbleApi

    init {
        val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val clientWithInterceptor = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.dribbble.com/v1/")
                .client(if (BuildConfig.DEBUG) clientWithInterceptor else OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        DRIBBBLE_API = retrofit.create(DribbbleApi::class.java)
    }

    fun getShots(page: Int): Call<List<Shot>> {
        return DRIBBBLE_API.getShots(page)
    }

    fun getSingleShot(shotId: Int): Call<Shot> {
        return DRIBBBLE_API.getSingleShot(shotId)
    }
}