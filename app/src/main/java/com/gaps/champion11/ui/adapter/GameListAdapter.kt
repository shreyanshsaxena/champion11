package com.gaps.champion11.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.model.GamesModel
import com.gaps.champion11.ui.BookingNumberActivity
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.SharedPrefUtils
import com.google.android.material.button.MaterialButton

class GameListAdapter (
    private val context: Context, list: List<GamesModel>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>(){
        private var gameList: List<GamesModel> = list
        var intent: Intent?=null

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val drawerItemTxt: TextView = itemView.findViewById(R.id.gameSlot)
            val bookNowUpcomingTxt: MaterialButton = itemView.findViewById(R.id.bookNowUpcoming)


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflator = LayoutInflater.from(parent.context)
            val itemView: View = inflator.inflate(R.layout.upcoming_game_item, parent, false)
            return ViewHolder(itemView)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val gameDataItem: GamesModel = gameList[holder.adapterPosition]
            holder.drawerItemTxt.text = AppUtil.getDateTimeFromString(gameDataItem.startTime).uppercase() + " to " + AppUtil.getDateTimeFromString(gameDataItem.endTime).uppercase()

            holder.bookNowUpcomingTxt.isEnabled = gameDataItem.isFutureGame
            holder.bookNowUpcomingTxt.setOnClickListener {
                SharedPrefUtils.setInt(context,SharedPrefUtils.SELECTED_GAME_ID,gameDataItem.gameId)
                intent = Intent(context, BookingNumberActivity::class.java)


            }
        }

    override fun getItemCount(): Int {
      return gameList.size
    }

}