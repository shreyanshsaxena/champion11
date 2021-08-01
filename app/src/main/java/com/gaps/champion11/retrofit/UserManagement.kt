package com.gaps.champion11.retrofit

import com.gaps.champion11.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserManagement {
    @POST("/api/user/register")
    fun registerUser(@Body req: RegisterUserRequest?): Call<ResponseDataModel?>?
    @POST("/api/user/verifyotp")
    fun verifyOTP(@Body req: VerifyOTPRequest?): Call<TokenResponseModel?>?
    @POST("/api/user/sendotp")
    fun sendOTP(@Body req: GetOTPRequest?): Call<ResponseBody?>?
    @GET("/api/user/getuser")
    fun getUserDetails(@Query("username") username:String?): Call<UserDetails?>?
    @GET("/api/bankdetails")
    fun saveUserBankDetails(@Query("username") username:String?): Call<UserDetails?>?



}