package com.gaps.champion11

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OTPViewModel : ViewModel() {
    private val timer:CountDownTimer

    private val _text = MutableLiveData<String>().apply {
         timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                value= (millisUntilFinished/1000).toString()
            }
            override fun onFinish() {

            }
        }
        timer.start()
    }
    fun startimer(){
        timer.start()
    }
    val text: LiveData<String> = _text

}