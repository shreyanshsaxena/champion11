package com.gaps.champion11.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.model.GamesModel

class GameListAdapter (
    private val context: Context, list: List<GamesModel>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>(){
        private var gameList: List<GamesModel> = list
        var intent: Intent?=null

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val drawerItemTxt: TextView = itemView.findViewById(R.id.gameSlot)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflator = LayoutInflater.from(parent.context)
            val itemView: View = inflator.inflate(R.layout.upcoming_game_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val gameDataItem: GamesModel = gameList[holder.adapterPosition]
            holder.drawerItemTxt.setText(gameDataItem.startTime)
            holder.itemView.setOnClickListener {
//                openRelevantFragment(drawerListItem)

            }
        }

    override fun getItemCount(): Int {
      return gameList.size
    }

}