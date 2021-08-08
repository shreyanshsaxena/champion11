package com.gaps.champion11.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityGameHistoryBinding
import com.gaps.champion11.ui.adapter.GameListAdapter
import com.gaps.champion11.ui.booking.BookingFragment

class GameHistoryActivity: BaseNavigationActivity() {
    private lateinit var binding: ActivityGameHistoryBinding
    private lateinit var gameHistoryAdapter:GameListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger_white)
        binding.championHeaderTxt.text = getString(R.string.welcome_header)

        binding.gamesHistoryList.layoutManager = LinearLayoutManager(context)
        gameHistoryAdapter = context?.let { GameListAdapter(it, gameList) }
        binding.gamesHistoryList.adapter = gameHistoryAdapter;
        if (savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, BookingFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

}