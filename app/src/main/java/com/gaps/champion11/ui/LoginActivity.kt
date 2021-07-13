package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityLoginBinding
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.model.GetOTPRequest
import com.gaps.champion11.model.TokenResponseModel
import com.gaps.champion11.model.VerifyOTPRequest
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.CommandCallbackWithFailure
import com.gaps.champion11.utils.HttpCode
import com.gaps.champion11.utils.SharedPrefUtils
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var disposableLogin: Disposable? = null
    private var disposableSignup: Disposable? = null
    private val loginButton: MaterialButton by lazy { findViewById(R.id.login) }
    private val signupbutton: TextView by lazy { findViewById(R.id.signup) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }
private fun handleError(){
    AppUtil.showDialogWithCallback(
        context = this@LoginActivity,
        "There is some issue in generating OTP.Please try again later",
        null,
        null,
        MessageType.ERROR,
        object : CommandCallbackWithFailure {
            override fun onSuccess() {
                AppUtil.hideAlertDialog()
            }
        })
}
    private fun getOTP(){
        showProgressDialog(this)
        val getOTPRequest = binding.userIDTextInputEditText.text.toString().let {
            GetOTPRequest(
                it
            )
        }
        val call = RetrofitApiClient.getApiInterfaceUser(this).sendOTP(getOTPRequest)

        call!!.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                hideProgressDialog()
                if (response.isSuccessful && response.code() == HttpCode.OK) {
                    val intent = Intent(this@LoginActivity, OTPActivity::class.java)
                    intent.putExtra("mobile", binding.userIDTextInputEditText.text.toString())
                    startActivity(intent)
                    finish()
                } else {
                    handleError()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                hideProgressDialog()
                handleError()
            }
        })
    }
    private fun setupListeners() {
        disposableLogin = loginButton.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (binding.userIDTextInputEditText.isNotNullOrEmpty(
                        binding.userIDTextInputEditText,
                        "Please enter a mobile number"
                    )
                ) {
                    if (binding.userIDTextInputEditText.isValidMobileNumber(
                            binding.userIDTextInputEditText,
                            "Mobile number entered is invalid"
                        )
                    ) {
                        getOTP()

                    }
                }
            }
        disposableSignup = signupbutton.clicks().throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this@LoginActivity, RegisterUserActivity::class.java))
            }
    }
}