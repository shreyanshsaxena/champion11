package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class BankDetailModel(
    @SerializedName("accountNo")
    val accountNo: String,
    @SerializedName("bank")
    val bank: String,
    @SerializedName("branch")
    val branch: String,
    @SerializedName("ifsc")
    val ifsc: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("upiId")
    val upiId: String?,
    @SerializedName("accountHolderName")
    val accountHolderName: String?
)