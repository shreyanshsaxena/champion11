package com.gaps.champion11

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.gaps.champion11.databinding.ActivityHomescreenBinding
import com.gaps.champion11.ui.BaseNavigationActivity
import com.gaps.champion11.ui.BookingNumberActivity
import com.gaps.champion11.ui.home.HomeFragment
import com.gaps.champion11.ui.wallet.WalletActivity
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class HomeScreenActivity : BaseNavigationActivity() {

    private lateinit var binding: ActivityHomescreenBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar

        init(this, toolbar,R.drawable.ic_hamburger)
        setupClickListeners()
        if (savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HomeFragment.newInstance())
                .commitAllowingStateLoss()


        }
    }

    private fun setupClickListeners() {
        binding.wallet.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this@HomeScreenActivity, WalletActivity::class.java))

            }
    }

    fun callBookingActivity(){
        startActivity(Intent(this@HomeScreenActivity, BookingNumberActivity::class.java))

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START) //CLOSE Nav Drawer!
        }
        else{
            super.onBackPressed()

        }
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