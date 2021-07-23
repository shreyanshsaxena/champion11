package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class TransactionModel (
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("walletBalance")
    val walletBalance:Int,
    @SerializedName("status")
    val status:Int,
    @SerializedName("userId")
    val userId:String,
    @SerializedName("id")
    val id:Int

)