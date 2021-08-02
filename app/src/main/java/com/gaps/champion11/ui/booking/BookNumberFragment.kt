package com.gaps.champion11.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gaps.champion11.databinding.FragmentBookingNumberBinding
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.model.OptionBet
import com.gaps.champion11.model.TransactionModel
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.ui.BookingNumberActivity
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.CommandCallbackWithFailure
import com.gaps.champion11.utils.HttpCode
import com.gaps.champion11.utils.SharedPrefUtils
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class BookNumberFragment : Fragment() {
    private lateinit var bookingViewModel: BookingViewModel
    private var _binding: FragmentBookingNumberBinding? = null
    val numberList = ArrayList<NumberDetail>()
    private var disposable: Disposable? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {

        fun newInstance(): BookNumberFragment {
            return BookNumberFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookingViewModel =
            ViewModelProvider(this).get(BookingViewModel::class.java)
        _binding = FragmentBookingNumberBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupListeners()
        return root
    }

    fun placeOptionBet() {
        val userId = context?.let { SharedPrefUtils.getString(it, SharedPrefUtils.USER_ID, null) }
        val gameId = context?.let { SharedPrefUtils.getInt(it, SharedPrefUtils.SELECTED_GAME_ID) }
        val userSelectedOption =
            context?.let { SharedPrefUtils.getString(it, SharedPrefUtils.SELECTED_NO, null) }
        if (!binding.amountTextInputEditText.text.toString().replace("\\s".toRegex(), "")
                .equals("")
        ) {
            val optionBet = OptionBet(
                binding.amountTextInputEditText.text.toString().toInt(),
                gameId,
                userSelectedOption,
                userId
            )
            postBetAmountOnNumber(optionBet)

        } else {
            AppUtil.onSnackCoordinate(
                binding.amountTextInputEditText,
                "Please enter an amount to place a bet"
            )
        }
    }


    private fun postBetAmountOnNumber(optionBet: OptionBet) {

        val call = RetrofitApiClient.getApiInterfaceTransaction(context).placeOptionBet(optionBet)
        call?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>?,
                response: Response<ResponseBody?>?
            ) {
                (activity as BookingNumberActivity?)!!.hideProgressDialog()
                if (response != null && response.isSuccessful) {
                    handleBetSuccess(optionBet)
                } else {
                    handleBetError()
                }

            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                (activity as BookingNumberActivity?)!!.hideProgressDialog()

                handleBetError()
            }

        }
        )
    }

    private fun fetchLatestWalletAmountFromServer() {
        context?.let { (activity as BookingNumberActivity?)!!.showProgressDialog(it) }
        val call = RetrofitApiClient.getApiInterfaceTransaction(context).getTransactionsList(0, 1)
        call.enqueue(object : Callback<List<TransactionModel>> {
            override fun onResponse(
                call: Call<List<TransactionModel>>,
                response: Response<List<TransactionModel>>
            ) {
                if (response.isSuccessful && response.code() == HttpCode.OK) {
                    if (response.body() != null) {
                        val transactionList = response.body()
                        if (transactionList[0].amount > 0) {
                            placeOptionBet()
                        } else {
                            (activity as BookingNumberActivity?)!!.hideProgressDialog()
                            AppUtil.onSnack(
                                binding.amountTextInputEditText,
                                "No sufficient amount in the wallet to place the bet"
                            )
                        }
                    }
                } else {
                    (activity as BookingNumberActivity?)!!.hideProgressDialog()
                    handleBetError()
                }
            }

            override fun onFailure(call: Call<List<TransactionModel>>, t: Throwable) {
                (activity as BookingNumberActivity?)!!.hideProgressDialog()
                handleBetError()
            }

        })
    }

    fun handleBetSuccess(optionBet: OptionBet) {
        context?.let {
            AppUtil.showDialogWithCallback(
                it,
                "Congratulations!! You have successfully placed a bet of Rs " + optionBet.amount + " on number " + context?.let {
                    SharedPrefUtils.getString(
                        it,
                        SharedPrefUtils.SELECTED_NO,
                        null
                    )
                },
                null,
                null,
                MessageType.SUCCESS,
                object : CommandCallbackWithFailure {
                    override fun onSuccess() {
                        AppUtil.hideAlertDialog()
                    }
                })
        }
    }

    fun handleBetError() {
        context?.let {
            AppUtil.showDialogWithCallback(
                it,
                "Sorry!! There was an issue in placing your bet. Please try again later after sometime",
                null,
                null,
                MessageType.ERROR,
                object : CommandCallbackWithFailure {
                    override fun onSuccess() {
                        AppUtil.hideAlertDialog()
                    }
                })
        }
    }

    fun setupListeners() {
        binding.luckyNo.text =
            context?.let { SharedPrefUtils.getString(it, SharedPrefUtils.SELECTED_NO, null) }
        disposable = binding.proceedNow.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                fetchLatestWalletAmountFromServer()
//                startActivity(Intent(this@Boo, HomeScreenAcivity::class.java))

            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}