package com.gaps.champion11.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.databinding.ActivitySplashBinding
import com.gaps.champion11.utils.SharedPrefUtils


class SplashScreenActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timer = object: CountDownTimer(1500, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if(SharedPrefUtils.getString(context = this@SplashScreenActivity,SharedPrefUtils.KEY_TOKEN,null)!=null){
                    startActivity(Intent(this@SplashScreenActivity, HomeScreenActivity::class.java))
                    finish()

                }
                else{
                    startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                    finish()
                }

            }
        }
        timer.start()
    }
}