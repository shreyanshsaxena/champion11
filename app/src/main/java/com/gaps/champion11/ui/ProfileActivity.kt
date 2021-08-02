package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityProfileBinding
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.model.UserDetails
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.CommandCallbackWithFailure
import com.gaps.champion11.utils.HttpCode
import com.gaps.champion11.utils.SharedPrefUtils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : BaseNavigationActivity() {
    private lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger)
        getUserDetails()


    }
    private fun getUserDetails() {
        showProgressDialog(context)
        val call = RetrofitApiClient.getApiInterfaceUser(this).getUserDetails(SharedPrefUtils.getString(context,SharedPrefUtils.MOBILE_NO,null))

        call!!.enqueue(object : Callback<UserDetails?> {
            override fun onResponse(
                call: Call<UserDetails?>,
                response: Response<UserDetails?>
            ) {
                hideProgressDialog()
                if (response.isSuccessful && response.code() == HttpCode.OK) {
                    val userDetails=response.body()
                    val userDetailString= Gson().toJson(userDetails, UserDetails::class.java)
                    binding.profileVal.userName.text= userDetails?.fullname
                    binding.profileVal.userEmail.text=userDetails?.email
                    binding.profileVal.userGender.text=AppUtil.getGenderValueString(userDetails?.gender)
                    binding.profileVal.userMobile.text=userDetails?.phonenumber
                    binding.profileVal.userState.text=userDetails?.state
                    SharedPrefUtils.setString(this@ProfileActivity, SharedPrefUtils.USER_ID, userDetails?.userid )
                    SharedPrefUtils.setString(this@ProfileActivity,
                        SharedPrefUtils.USER_DETAILS,userDetailString)

                } else {
                    AppUtil.showDialogWithCallback(
                        context = this@ProfileActivity,
                        "There is some issue in fetching user details.Please try again later after sometime",
                        null,
                        null,
                        MessageType.ERROR,
                        object : CommandCallbackWithFailure {
                            override fun onSuccess() {
                                AppUtil.hideAlertDialog()
                                finish()
                            }
                        })

                }
            }

            override fun onFailure(call: Call<UserDetails?>, t: Throwable) {
                hideProgressDialog()
                AppUtil.showDialogWithCallback(
                    context = this@ProfileActivity,
                    "There is some issue in fetching user details.Please try again later after sometime",
                    null,
                    null,
                    MessageType.ERROR,
                    object : CommandCallbackWithFailure {
                        override fun onSuccess() {
                            AppUtil.hideAlertDialog()
                            finish()
                        }
                    })
            }
        })

    }


}