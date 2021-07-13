package com.gaps.champion11.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.utils.SharedPrefUtils

class NumberListAdapter(private val numberList: List<NumberDetail>, val context: Context?) :
    RecyclerView.Adapter<NumberListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_view, parent, false)

        // return the view holder
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the current animal
        if (numberList[position].isSelected) {
            if (context != null) {
                holder.numberTxt.background =
                    (ResourcesCompat.getDrawable(context.resources, R.drawable.borderallred, null))
            }
            holder.numberTxt.setTextColor(Color.WHITE)
        } else {
            if (context != null) {
                holder.numberTxt.background =
                    (ResourcesCompat.getDrawable(context.resources, R.drawable.borderall, null))
            }
            holder.numberTxt.setTextColor(Color.BLACK)
        }
        holder.numberTxt.text = numberList[position].number
        holder.numberTxt.setOnClickListener {
            for (i in numberList) {
                i.isSelected = i.number == numberList[position].number
                if (context != null && i.isSelected)
                    SharedPrefUtils.setString(
                        context,
                        SharedPrefUtils.SELECTED_NO,
                        i.number.toString()
                    )
            }
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return numberList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberTxt: TextView = itemView.findViewById(R.id.numberTxt)
    }


    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}



