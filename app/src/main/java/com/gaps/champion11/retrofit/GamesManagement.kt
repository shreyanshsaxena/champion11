package com.gaps.champion11.retrofit

import com.gaps.champion11.model.GamesModel
import retrofit2.Call
import retrofit2.http.GET

interface GamesManagement {
    @GET("/api/Games")
    fun getGamesList(): Call<List<GamesModel>>
}