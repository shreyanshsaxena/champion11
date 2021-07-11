package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityProfileBinding
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProfileActivity : AppCompatActivity() {
     private var disposableLogin: Disposable?=null
    private lateinit var binding: ActivityProfileBinding
    private val createProfileBtn: MaterialButton by lazy { findViewById(R.id.createProfile) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()


    }
    private fun setupListeners(){
        disposableLogin = createProfileBtn.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this@ProfileActivity, HomeScreenActivity::class.java))

            }
    }

}