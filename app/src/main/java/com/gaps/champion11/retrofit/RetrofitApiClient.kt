package com.gaps.champion11.retrofit


import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.gaps.champion11.utils.Keys
import com.gaps.champion11.ui.LoginActivity
import com.gaps.champion11.utils.SharedPrefUtils
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.*
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitApiClient {
    private const val TIMEOUT = 1
    private val httpClient: Builder = Builder()
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://champion11.azurewebsites.net")
        .addConverterFactory(ToStringConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
    private var updateUIHandler: Handler? = null


    fun getApiInterfaceUser(ctx: Context): UserManagement {
        return getRetrofitInstance(ctx).create(UserManagement::class.java)
    }


    private fun getRetrofitInstance(ctx: Context): Retrofit {
        if (updateUIHandler == null) {
            updateUIHandler = Handler(Looper.getMainLooper())
        }
        val token: String? = SharedPrefUtils.getString(ctx, SharedPrefUtils.KEY_TOKEN, "")
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.writeTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader(Keys.ACCEPT, "application/json")
                .addHeader(Keys.CONTENT_TYPE, "application/json")
                .header(Keys.AUTHORIZATION, token)
                .method(original.method(), original.body())
            val request: Request = requestBuilder.build()
            val response: Response = chain.proceed(request)
            response
        }
        val client: OkHttpClient = httpClient.build()
        return builder.client(client).build()
    }
}