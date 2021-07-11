package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityLoginBinding
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
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
                        val intent = Intent(this@LoginActivity, OTPActivity::class.java)
                        intent.putExtra("mobile", binding.userIDTextInputEditText.text.toString())
                        startActivity(intent)
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