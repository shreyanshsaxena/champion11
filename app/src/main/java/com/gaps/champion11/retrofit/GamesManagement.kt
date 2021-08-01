package com.gaps.champion11.retrofit

import com.gaps.champion11.model.GameOptionBetUser
import com.gaps.champion11.model.GamesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesManagement {
    @GET("/api/Games")
    fun getGamesList(): Call<List<GamesModel>>

    @GET("/api/games/optionbets")
    fun getGameOptionBets(@Query("gameId") gameId: Int): Call<List<GameOptionBetUser>>
}