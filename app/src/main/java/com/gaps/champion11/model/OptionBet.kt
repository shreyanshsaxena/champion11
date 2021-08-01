package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName


data class OptionBet(@SerializedName("Amount") var amount: Int?, @SerializedName("GameId") var gameId: Int?, @SerializedName("UserSelectedOption") var userSelectedOption: String?,  @SerializedName("UserId") var userId:String?)
