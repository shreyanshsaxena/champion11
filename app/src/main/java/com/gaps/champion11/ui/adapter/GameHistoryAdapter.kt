package com.gaps.champion11.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.model.UserStatsResponse
import com.gaps.champion11.ui.GameHistoryActivity
import com.gaps.champion11.utils.AppUtil

class GameHistoryAdapter
    (
    private val context: Context, list: List<UserStatsResponse>) :
    RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>(){
    private var gameList: List<UserStatsResponse> = list

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gameDate: TextView = itemView.findViewById(R.id.gameDate)
        val gameSlotTime: TextView = itemView.findViewById(R.id.gameSlotTime)
        val pattiNo: TextView = itemView.findViewById(R.id.pattiNo)
        val betAmount: TextView = itemView.findViewById(R.id.betAmt)
        val viewDetailsBtn: TextView = itemView.findViewById(R.id.viewDetailsBtn)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemView: View = inflator.inflate(R.layout.game_history_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameDataItem: UserStatsResponse = gameList[holder.adapterPosition]
        holder.pattiNo.text= gameDataItem.pattiNo
        holder.betAmount.text=gameDataItem.userGameAmount.toString()
        holder.gameDate.text=AppUtil.getDateFromString(gameDataItem.startTime)
        holder.gameSlotTime.text=AppUtil.getDateTimeFromString(gameDataItem.startTime).uppercase() + " to " + AppUtil.getDateTimeFromString(gameDataItem.endTime).uppercase()
        holder.viewDetailsBtn.setOnClickListener {
        (context as GameHistoryActivity?)!!.getOptionBets(gameDataItem)
        }
        }

    override fun getItemCount(): Int {
        return gameList.size
    }

}