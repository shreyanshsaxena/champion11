package com.gaps.champion11.utils

interface CommandCallbackWithFailure {
    fun onSuccess()
    fun onFailure() {}
}