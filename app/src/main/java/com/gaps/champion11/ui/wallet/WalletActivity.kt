package com.gaps.champion11.ui.wallet

import android.os.Bundle
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityWalletBinding
import com.gaps.champion11.ui.BaseNavigationActivity
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class WalletActivity : BaseNavigationActivity(){

    private var disposableLogin: Disposable?=null
    private lateinit var binding: ActivityWalletBinding
    private val createProfileBtn: MaterialButton by lazy { findViewById(R.id.createProfile) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }
    private fun setupListeners(){
        disposableLogin = createProfileBtn.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
//                startActivity(Intent(this@ProfileActivity, HomeScreenActivity::class.java))

            }
    }
}