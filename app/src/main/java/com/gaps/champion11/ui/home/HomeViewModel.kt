package com.gaps.champion11.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "10:59 PM to 10:50 AM"
    }
    val text: LiveData<String> = _text

}