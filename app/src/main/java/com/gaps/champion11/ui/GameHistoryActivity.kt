package com.gaps.champion11.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityGameHistoryBinding
import com.gaps.champion11.model.GameOptionBetUser
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.model.UserStatsResponse
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.ui.adapter.GameHistoryAdapter
import com.gaps.champion11.utils.AppUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameHistoryActivity : BaseNavigationActivity() {
    val numberList = ArrayList<NumberDetail>()
    private var optionDataBody: List<GameOptionBetUser>? = null
    private val arr = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    private var userStatList: List<UserStatsResponse> = ArrayList()
    private lateinit var binding: ActivityGameHistoryBinding
    private lateinit var gameHistoryAdapter: GameHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger_white)
        userStatList = ArrayList()

        getPlayedGameListHistory()
    }


    fun getOptionBets(gameDataItem: UserStatsResponse) {
        numberList.clear()
        for (i in arr) {
            val numberDetail = NumberDetail(i, false, false, 0);
            numberList.add(numberDetail)
        }

        showProgressDialog(context)
        val gameId = gameDataItem.gameId
        val call = RetrofitApiClient.getApiInterfaceGames(context).getGameOptionBets((gameId))
        call.enqueue(object : Callback<List<GameOptionBetUser>> {
            override fun onResponse(
                call: Call<List<GameOptionBetUser>>?,
                response: Response<List<GameOptionBetUser>>?
            ) {
                hideProgressDialog()
                if (response != null && response.isSuccessful) {
                    optionDataBody = response.body()
                    if (optionDataBody != null) {

                        for (i in numberList) {
                            for (j in optionDataBody!!) {
                                if (i.number.equals(j.option.toString())) {
                                    i.isOptionBetUser = true
                                    i.betAmount = j.amount

                                }
                            }
                        }
                        AppUtil.showGameDetailDialogWithCallback(context,numberList,gameDataItem)
                    }

                }

            }

            override fun onFailure(call: Call<List<GameOptionBetUser>>?, t: Throwable?) {
                hideProgressDialog()

            }

        })
    }

    private fun getPlayedGameListHistory() {
        showProgressDialog(context)
        val call = RetrofitApiClient.getApiInterfaceGames(context).getUserGameList()
        call.enqueue(object : Callback<List<UserStatsResponse>> {
            override fun onResponse(
                call: Call<List<UserStatsResponse>>,
                response: Response<List<UserStatsResponse>>
            ) {
                hideProgressDialog()
                if (response.isSuccessful && response.body() != null && response.body()
                        .isNotEmpty()
                ) {
                    userStatList = response.body()
                    binding.gamesHistoryList.layoutManager = LinearLayoutManager(context)
                    gameHistoryAdapter = GameHistoryAdapter(context, userStatList)
                    binding.gamesHistoryList.adapter = gameHistoryAdapter;
                }

            }

            override fun onFailure(call: Call<List<UserStatsResponse>>, t: Throwable?) {
                hideProgressDialog()

            }

        })
    }


}