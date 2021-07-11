package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityRegisterBinding
import com.gaps.champion11.model.RegisterUserRequest
import com.gaps.champion11.model.ResponseDataModel
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.CommandCallbackWithValue
import com.gaps.champion11.utils.HttpCode
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RegisterUserActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var disposable: Disposable? = null
    private var disposableState: Disposable? = null

    private val register: MaterialButton by lazy { findViewById(R.id.register) }
    private val arr = listOf(
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chhattisgarh",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Nadu",
        "Telangana",
        "Tripura",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {

        disposableState = binding.stateUser.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                AppUtil.showBottomSheetDialog(this, arr, "Select State",
                    object : CommandCallbackWithValue {
                        override fun onSuccess(value: String, pos: Int) {
                            binding.stateUser.text = value
                        }
                    })
            }

        disposable = register.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                registerUser()

            }
    }

    private fun registerUser() {
        showProgressDialog(this)
        val registerUserRequest = RegisterUserRequest(
            binding.mobileuser.text.toString(),
            binding.nameuser.text.toString(),
            null, binding.stateUser.toString()
        )
        val call = RetrofitApiClient.getApiInterfaceUser(this).registerUser(registerUserRequest)

        call!!.enqueue(object : Callback<ResponseDataModel?> {
            override fun onResponse(
                call: Call<ResponseDataModel?>,
                response: Response<ResponseDataModel?>
            ) {
                hideProgressDialog()
                if (response.isSuccessful && response.code() == HttpCode.CREATED) {
                    if (response.body() != null) {
                        startActivity(Intent(this@RegisterUserActivity, LoginActivity::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseDataModel?>, t: Throwable) {
                hideProgressDialog()
            }
        })
    }


}
