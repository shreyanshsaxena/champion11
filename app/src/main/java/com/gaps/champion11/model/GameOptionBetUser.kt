package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class GameOptionBetUser (
    @SerializedName("option")
    val option: Int,
    @SerializedName("amount")
    val amount:Int,
    @SerializedName("userId")
    val userId:String,
    @SerializedName("gameId")
    val gameId:Int,

)