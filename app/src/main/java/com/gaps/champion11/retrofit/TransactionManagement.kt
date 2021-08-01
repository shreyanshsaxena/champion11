package com.gaps.champion11.retrofit

import com.gaps.champion11.model.OptionBet
import com.gaps.champion11.model.RegisterUserRequest
import com.gaps.champion11.model.ResponseDataModel
import com.gaps.champion11.model.TransactionModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TransactionManagement {
    @GET("/api/transactions")
    fun getTransactionsList(
        @Query("skip") skip: Int?,
        @Query("take") take: Int?
    ): Call<List<TransactionModel>>

    @POST("/api/transactions")
    fun placeOptionBet(@Body req: OptionBet?): Call<ResponseBody?>?
}