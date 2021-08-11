package com.gaps.champion11.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.gaps.champion11.R
import com.gaps.champion11.model.DrawerListItem
import com.gaps.champion11.model.UserDetails
import com.gaps.champion11.utils.SharedPrefUtils
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.hcl.iatm.Adapter.DrawerAdapter
import java.text.MessageFormat
import java.util.*

open class BaseNavigationActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    public lateinit var context: Context;
    internal val drawer: DrawerLayout by lazy { findViewById(R.id.drawer_layout) }

    fun init(context: Context, toolbar: Toolbar, @DrawableRes drawIcon: Int) {
        this.context = context
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.elevation = 0f
        }
        drawer.setScrimColor(resources.getColor(android.R.color.transparent))
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                drawerView.bringToFront()
            }

        }
        toggle.isDrawerSlideAnimationEnabled = true
        drawer.addDrawerListener(toggle)
        val drawable: Drawable? = VectorDrawableCompat.create(
            resources, drawIcon,
            theme
        )
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(drawable)
        toggle.syncState()
        toggle.toolbarNavigationClickListener = View.OnClickListener { v: View? ->
            drawer.openDrawer(
                GravityCompat.START
            )
        }
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = layoutInflater.inflate(R.layout.nav_header_nav_drawer, null, false)
        val headerName = headerView.findViewById<TextView>(R.id.userName)
        val headerEmail = headerView.findViewById<TextView>(R.id.userEmail)
        val headerMobile = headerView.findViewById<TextView>(R.id.userMobile)

        val userDetails = SharedPrefUtils.getString(context, SharedPrefUtils.USER_DETAILS, null)
        val userDetailsObject = Gson().fromJson(userDetails, UserDetails::class.java)
        headerName.text = userDetailsObject.fullname
        headerEmail.text=userDetailsObject.email
        headerMobile.text=userDetailsObject.phonenumber

        //Home and logout will always be enabled
        val drawerRecyclerView: RecyclerView = navigationView.findViewById(R.id.drawer_list)
        drawerRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val drawerListItems: MutableList<DrawerListItem> = ArrayList<DrawerListItem>()
        val drawerListItem = DrawerListItem("Bank Account", R.drawable.ic_hotel, true)

        drawerListItems.add(drawerListItem)
        val drawerListItem5 = DrawerListItem("My Game History", R.drawable.ic_hotel, true)

        drawerListItems.add(drawerListItem5)
        val drawerListItem1 = DrawerListItem("About Us", R.drawable.ic_user_alt, true)
        drawerListItems.add(drawerListItem1)
        val drawerListItem2 = DrawerListItem("Privacy Policy", R.drawable.ic_edit, true)
        drawerListItems.add(drawerListItem2)
        val drawerListItem3 =
            DrawerListItem("Terms and Conditions", R.drawable.ic_passport, true)
        drawerListItems.add(drawerListItem3)
        val drawerListItem4 = DrawerListItem("Log Out", R.drawable.ic_logouticon, true)
        drawerListItems.add(drawerListItem4)
        val drawerAdapter = DrawerAdapter(context, drawerListItems, drawerLayout = drawer)
        drawerRecyclerView.adapter = drawerAdapter;
        navigationView.addHeaderView(headerView)
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}