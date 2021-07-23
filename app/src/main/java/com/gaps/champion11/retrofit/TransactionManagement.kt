package com.gaps.champion11.retrofit

import com.gaps.champion11.model.RegisterUserRequest
import com.gaps.champion11.model.ResponseDataModel
import com.gaps.champion11.model.TransactionModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionManagement {
    @GET("/api/transactions")
    fun getTransactionsList(): Call<List<TransactionModel>?>?
}