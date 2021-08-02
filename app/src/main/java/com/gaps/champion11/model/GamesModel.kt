package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class GamesModel (
    @SerializedName("gameId")
    val gameId: Int,
    @SerializedName("startTime")
    val startTime:String,
    @SerializedName("endTime")
    val endTime:String,
    @SerializedName("priceAmount")
    val priceAmount:Float,
    @SerializedName("isCurrentGame")
    val isCurrentGame:Boolean,
    @SerializedName("isFutureGame")
    val isFutureGame:Boolean,
    @SerializedName("pattiResult")
    val pattiResult:String,
    @SerializedName("correctOption")
    val correctOption:String

)
