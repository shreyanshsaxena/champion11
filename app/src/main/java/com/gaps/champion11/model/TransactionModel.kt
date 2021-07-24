package com.gaps.champion11.model

import com.gaps.champion11.enum.Status
import com.gaps.champion11.enum.WalletStatus
import com.google.gson.annotations.SerializedName

data class TransactionModel (
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("walletBalance")
    val walletBalance:Int,
    @SerializedName("walletStatus")
    val walletStatus:WalletStatus,
    @SerializedName("status")
    val status:Status,
    @SerializedName("userId")
    val userId:String,
    @SerializedName("id")
    val id:Int

)