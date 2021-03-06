package com.hcl.iatm.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.R
import com.gaps.champion11.model.DrawerListItem
import com.gaps.champion11.ui.GameHistoryActivity
import com.gaps.champion11.ui.LoginActivity
import com.gaps.champion11.ui.PolicyActivity
import com.gaps.champion11.ui.bankdetails.AddBankDetailsActivity
import com.gaps.champion11.utils.SharedPrefUtils
import com.hcl.iatm.Utils.StringConstants

class DrawerAdapter(
    private val context: Context, list: List<DrawerListItem>, private val drawerLayout: DrawerLayout?) :
    RecyclerView.Adapter<DrawerAdapter.ViewHolder>(){
    private var drawerListItems: List<DrawerListItem> = list
    var intent: Intent?=null



class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val drawerItemTxt: TextView = itemView.findViewById(R.id.drawerItemTxt)
        var drawerItemImg: ImageView= itemView.findViewById(R.id.drawerItemImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemView: View = inflator.inflate(R.layout.drawer_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drawerListItem: DrawerListItem = drawerListItems[holder.adapterPosition]
        holder.drawerItemImg!!.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                drawerListItem.icon
            )
        )
        holder.drawerItemTxt.setText(drawerListItem.name)

        holder.itemView.setOnClickListener {
            openRelevantFragment(drawerListItem)

        }
    }

    private fun openRelevantFragment(drawerListItem: DrawerListItem) {
        intent = null
        when (drawerListItem.name) {
            "Bank Account" -> {
                intent = Intent(context, AddBankDetailsActivity::class.java)
            }
            "My Game History" ->{
                intent = Intent(context, GameHistoryActivity::class.java)
            }
            "Log Out" -> {
                SharedPrefUtils.clearAll(context)
                intent = Intent(context, LoginActivity::class.java)
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            "Terms and Conditions"->{
                 intent = Intent(context, PolicyActivity::class.java)
                intent!!.putExtra(StringConstants.DISPLAY_CONTENT_TYPE, "tnc")
            }
            "About Us"->{
                 intent = Intent(context, PolicyActivity::class.java)
                intent!!.putExtra(StringConstants.DISPLAY_CONTENT_TYPE, "about")
            }
            "Privacy Policy"->{
                 intent = Intent(context, PolicyActivity::class.java)
                intent!!.putExtra(StringConstants.DISPLAY_CONTENT_TYPE, "privacy")
            }
        }
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START) //CLOSE Nav Drawer!
            }
        }
        if (intent != null) context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return drawerListItems.size
    }

}