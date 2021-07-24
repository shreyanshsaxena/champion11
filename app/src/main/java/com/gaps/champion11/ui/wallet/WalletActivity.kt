package com.gaps.champion11.ui.wallet

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityWalletBinding
import com.gaps.champion11.model.GamesModel
import com.gaps.champion11.model.TransactionModel
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.ui.BaseNavigationActivity
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.HttpCode
import com.google.android.material.button.MaterialButton
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.MessageFormat
import java.util.concurrent.TimeUnit

class WalletActivity : BaseNavigationActivity() {

    private var disposableLogin: Disposable? = null
    private lateinit var binding: ActivityWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar

        init(this, toolbar, R.drawable.ic_hamburger_white)
        setupListeners()
        val call = RetrofitApiClient.getApiInterfaceTransaction(this).getTransactionsList(0, 1)
        call.enqueue(object : Callback<List<TransactionModel>> {
            override fun onResponse(
                call: Call<List<TransactionModel>>,
                response: Response<List<TransactionModel>>
            ) {
                if (response.isSuccessful && response.code() == HttpCode.OK) {
                    if (response.body() != null) {
                        val transactionList = response.body()
                        binding.walletBalanceTxt.text=MessageFormat.format("{0} INR",transactionList[0].walletBalance)
                    }
                }
            }

            override fun onFailure(call: Call<List<TransactionModel>>, t: Throwable) {

            }

        })

    }

    private fun setupListeners() {
    }
}