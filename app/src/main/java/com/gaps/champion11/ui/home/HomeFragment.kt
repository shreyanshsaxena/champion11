package com.gaps.champion11.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.databinding.FragmentHomeBinding
import com.gaps.champion11.model.GamesModel
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.ui.adapter.GameListAdapter
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.HttpCode
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var disposableLogin: Disposable? = null
    private var responseDataModel: List<GamesModel>? = null
    private var gameList: MutableList<GamesModel> = ArrayList()
    var upcomingGameAdapter:GameListAdapter?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.currentSlotTime
        setupListeners()
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        AppUtil.setSpnnableStringFormat(binding.twentyfourseven, context)
        getGamesList()
        binding.upcomingGameList.layoutManager = LinearLayoutManager(context)
        upcomingGameAdapter = context?.let { GameListAdapter(it, gameList) }
        binding.upcomingGameList.adapter = upcomingGameAdapter;
        return root
    }

    private fun getGamesList() {
        context?.let { (activity as HomeScreenActivity).showProgressDialog(it) }

        if (context != null) {
            val call = RetrofitApiClient.getApiInterfaceGames(context).getGamesList()
            call.enqueue(object : Callback<List<GamesModel>> {
                override fun onResponse(
                    call: Call<List<GamesModel>>,
                    response: Response<List<GamesModel>>
                ) {
                    (activity as HomeScreenActivity).hideProgressDialog()
                    if (response.isSuccessful && response.code() == HttpCode.OK) {
                        responseDataModel = response.body()
                        if (responseDataModel?.get(0)?.isCurrentGame == true) {
                            binding.noCurrentSlot.visibility = View.GONE
                            binding.currentSlotLayout.visibility = View.VISIBLE
                            homeViewModel._text.value = AppUtil.getDateTimeFromString(
                                responseDataModel?.get(
                                    0
                                )?.startTime
                            ).uppercase() + " to " + AppUtil.getDateTimeFromString(
                                responseDataModel?.get(0)?.endTime
                            ).uppercase()
                        } else {
                            binding.noCurrentSlot.visibility = View.VISIBLE
                            binding.currentSlotLayout.visibility = View.GONE
                        }
                        for( i in response.body()){
                            if(!i.isCurrentGame){
                               gameList.add(i)
                            }
                        }
                        upcomingGameAdapter?.notifyDataSetChanged()

                    } else {
                        handleError()
                    }
                }

                override fun onFailure(call: Call<List<GamesModel>>, t: Throwable) {
                    (activity as HomeScreenActivity).hideProgressDialog()
                    handleError()
                }
            })
        }

    }

    private fun handleError() {
        TODO("Not yet implemented")
    }

    private fun setupListeners() {
        disposableLogin = binding.bookCurrentSlot.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (activity as HomeScreenActivity).callBookingActivity()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}