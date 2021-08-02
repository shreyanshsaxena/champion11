package com.gaps.champion11.model

import com.google.gson.annotations.SerializedName

data class BankDetailModel(
    @SerializedName("AccountNo")
    val accountNo: String,
    @SerializedName("Bank")
    val bank: String,
    @SerializedName("Branch")
    val branch: String,
    @SerializedName("IFSC")
    val ifsc: String,
    @SerializedName("Address")
    val address: String,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("UpiId")
    val upiId: String?
)