package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.OTPViewModel
import com.gaps.champion11.databinding.ActivityOtpBinding
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.model.TokenResponseModel
import com.gaps.champion11.model.UserDetails
import com.gaps.champion11.model.VerifyOTPRequest
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.utils.*
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.MessageFormat
import java.util.concurrent.TimeUnit

class OTPActivity : BaseActivity() {
    private lateinit var binding: ActivityOtpBinding
    private lateinit var otpModel: OTPViewModel
    private var disposableResendOTP: Disposable? = null
    var mobile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mobile = intent.getStringExtra("mobile").toString()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        otpModel =
            ViewModelProvider(this).get(OTPViewModel::class.java)
        setupListenersAndObservers()
    }

    private fun getUserDetails() {
        val call = RetrofitApiClient.getApiInterfaceUser(this).getUserDetails(mobile)

        call!!.enqueue(object : Callback<UserDetails?> {
            override fun onResponse(
                call: Call<UserDetails?>,
                response: Response<UserDetails?>
            ) {
                hideProgressDialog()
                if (response.isSuccessful && response.code() == HttpCode.OK) {
                    val userDetails=response.body()
                    val userDetailString=Gson().toJson(userDetails,UserDetails::class.java)

                    SharedPrefUtils.setString(this@OTPActivity,SharedPrefUtils.USER_ID, userDetails?.userid)
                    SharedPrefUtils.setString(this@OTPActivity,SharedPrefUtils.USER_DETAILS,userDetailString)
                    SharedPrefUtils.setString(this@OTPActivity,SharedPrefUtils.MOBILE_NO,userDetails?.username)
                    startActivity(Intent(this@OTPActivity, HomeScreenActivity::class.java))
                    finish()

                } else {
                    hideProgressDialog()
                    AppUtil.showDialogWithCallback(
                        context = this@OTPActivity,
                        "There is some issue in login into the application.Please try again later",
                        null,
                        null,
                        MessageType.ERROR,
                        object : CommandCallbackWithFailure {
                            override fun onSuccess() {
                                AppUtil.hideAlertDialog()
                            }
                        })

                }
            }

            override fun onFailure(call: Call<UserDetails?>, t: Throwable) {
                hideProgressDialog()
                AppUtil.showDialogWithCallback(
                    context = this@OTPActivity,
                    "There is some issue in login into the application.Please try again later",
                    null,
                    null,
                    MessageType.ERROR,
                    object : CommandCallbackWithFailure {
                        override fun onSuccess() {
                            AppUtil.hideAlertDialog()
                        }
                    })
            }
        })

    }

    private fun verifyOTP() {
        showProgressDialog(this)
        val verifyOTPRequest = mobile?.let {
            VerifyOTPRequest(
                it, binding.otpTxt.text.toString()
            )
        }
        val call = RetrofitApiClient.getApiInterfaceUser(this).verifyOTP(verifyOTPRequest)

        call!!.enqueue(object : Callback<TokenResponseModel?> {
            override fun onResponse(
                call: Call<TokenResponseModel?>,
                response: Response<TokenResponseModel?>
            ) {
                if (response.isSuccessful && response.code() == HttpCode.OK) {
                    SharedPrefUtils.setString(
                        this@OTPActivity,
                        SharedPrefUtils.KEY_TOKEN,
                        response.body()!!.token
                    )
                    getUserDetails()
                } else {
                    hideProgressDialog()
                    AppUtil.showDialogWithCallback(
                        context = this@OTPActivity,
                        "There is some issue in login into the application.Please try again later",
                        null,
                        null,
                        MessageType.ERROR,
                        object : CommandCallbackWithFailure {
                            override fun onSuccess() {
                                AppUtil.hideAlertDialog()
                            }
                        })

                }
            }

            override fun onFailure(call: Call<TokenResponseModel?>, t: Throwable) {
                hideProgressDialog()
                AppUtil.showDialogWithCallback(
                    context = this@OTPActivity,
                    "There is some issue in login into the application.Please try again later",
                    null,
                    null,
                    MessageType.ERROR,
                    object : CommandCallbackWithFailure {
                        override fun onSuccess() {
                            AppUtil.hideAlertDialog()
                        }
                    })
            }
        })
    }

    private fun setupListenersAndObservers() {
        //resend otp wait text
        otpModel.text.observe(this, {
            if (it == "0") {
                binding.resendOTPBtn.visibility = View.VISIBLE
                binding.timer.visibility = View.GONE
            }
            binding.timer.text = MessageFormat.format("Please resend the OTP after {0} seconds", it)
        })
        //click listener for resend OTP
        disposableResendOTP = binding.resendOTPBtn.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.resendOTPBtn.visibility = View.GONE
                binding.timer.visibility = View.VISIBLE
                otpModel.startimer()
            }
        disposableResendOTP = binding.login.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                verifyOTP()
            }

    }
}