package com.gaps.champion11.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.databinding.FragmentBookingBinding
import com.gaps.champion11.model.GameOptionBetUser
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.ui.BookingNumberActivity
import com.gaps.champion11.ui.adapter.NumberListAdapter
import com.gaps.champion11.utils.SharedPrefUtils
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class BookingFragment : Fragment() {
    private lateinit var bookingViewModel: BookingViewModel
    private var _binding: FragmentBookingBinding? = null
    private var disposableBookNow: Disposable? = null
    private val arr = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    var root: View? = null
    val numberList = ArrayList<NumberDetail>()
    private var optionDataBody: List<GameOptionBetUser>? = null
    private var numberListAdapter:NumberListAdapter?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {

        fun newInstance(): BookingFragment {
            return BookingFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as BookingNumberActivity?)!!.setHeaderTextBookingActivity()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (root == null) {
            bookingViewModel =
                ViewModelProvider(this).get(BookingViewModel::class.java)
            _binding = FragmentBookingBinding.inflate(inflater, container, false)
            root = binding.root
            setupListeners()

        }

        return root as View
    }

    private fun getOptionBets() {

        val gameId = context?.let { SharedPrefUtils.getInt(it, SharedPrefUtils.SELECTED_GAME_ID) }
        val call = RetrofitApiClient.getApiInterfaceGames(context).getGameOptionBets((gameId!!))
        call.enqueue(object : Callback<List<GameOptionBetUser>> {
            override fun onResponse(
                call: Call<List<GameOptionBetUser>>?,
                response: Response<List<GameOptionBetUser>>?
            ) {
                if (response != null && response.isSuccessful) {
                    optionDataBody = response.body()
                    if (optionDataBody != null) {

                        for (i in arr) {
                            for (j in optionDataBody!!) {
                                if (i == j.option.toString()){
                                    for (a in numberList) {
                                        a.isOptionBetUser=true
                                        a.betAmount=a.betAmount
                                    }
                                }
                            }
                        }
                        numberListAdapter?.notifyDataSetChanged()
                    }

                }

            }

            override fun onFailure(call: Call<List<GameOptionBetUser>>?, t: Throwable?) {
            }

        })
    }

    private fun handleError() {
    }

    private fun setupUI() {
        for (i in arr) {
            val numberDetail = NumberDetail(i, false, false,0);
            numberList.add(numberDetail)
        }
        val gridLayoutManager = GridLayoutManager(activity, 5, RecyclerView.VERTICAL, false)
        binding.numberList.layoutManager = gridLayoutManager

         numberListAdapter = NumberListAdapter(numberList, context)
        binding.numberList.isNestedScrollingEnabled = false
        binding.numberList.adapter = numberListAdapter

        getOptionBets()
    }

    private fun setupListeners() {
        numberList.clear()
        setupUI()
        disposableBookNow = binding.bookNow.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (activity as BookingNumberActivity?)!!.loadBookingNumberFragment()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}