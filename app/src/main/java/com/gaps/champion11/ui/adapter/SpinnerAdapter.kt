package com.gaps.champion11.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.CommandCallbackWithValue

class SpinnerAdapter(private val context: Context?, private val arrayList: List<String>,
                     private var commandCallback: CommandCallbackWithValue
    ) :
        RecyclerView.Adapter<SpinnerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.tvName.text = arrayList[position]
            holder.itemView.setOnClickListener { v: View? ->
                AppUtil.dismissBottomSheetDialog();
                commandCallback.onSuccess(
                    arrayList[position], position
                )
            }
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvName: TextView = itemView.findViewById(R.id.tv_Name)

        }

}
