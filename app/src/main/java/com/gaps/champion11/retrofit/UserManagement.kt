package com.gaps.champion11.retrofit

import com.gaps.champion11.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserManagement {
    @POST("/user-management/v1/login")
    fun registerUser(@Body req: RegisterUserRequest?): Call<ResponseDataModel?>?
    @POST("/api/user/verifyotp")
    fun verifyOTP(@Body req: VerifyOTPRequest?): Call<TokenResponseModel?>?
    @POST("/api/user/getuser")
    fun getUserDetails(@Query("username") username:String?): Call<UserDetails?>?
}