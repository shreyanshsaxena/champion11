package com.gaps.champion11.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityBookingBinding
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.ui.booking.BookNumberFragment
import com.gaps.champion11.ui.booking.BookingFragment

class BookingNumberActivity : BaseNavigationActivity() {
    private lateinit var binding: ActivityBookingBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    val numberList = ArrayList<NumberDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger_white)
        binding.championHeaderTxt.text = getString(R.string.welcome_header)

        if (savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, BookingFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

    fun setHeaderTextBookingActivity(){
        binding.championHeaderTxt.text = getString(R.string.welcome_header)

    }
    fun loadBookingNumberFragment(){
        binding.championHeaderTxt.text = getString(R.string.staking_text)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, BookNumberFragment.newInstance()).addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}