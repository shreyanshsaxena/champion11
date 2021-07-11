package com.gaps.champion11.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.ui.adapter.SpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class AppUtil {

    companion object {
        var alertMessage: String? = null

        private lateinit var mBottomSheetDialog: Any
        private var alertDialog: AlertDialog? = null

        fun dismissBottomSheetDialog() {
            (mBottomSheetDialog as BottomSheetDialog).dismiss()
        }

        fun setSpnnableStringFormat(textview: TextView, context: Context?) {
            val colored = "24x7"
            val builder = SpannableStringBuilder()
            builder.append("for ")
            val start = builder.length
            builder.append(colored)
            val end = builder.length
            if (context != null) {
                builder.setSpan(
                    ForegroundColorSpan(context.resources.getColor(android.R.color.holo_red_dark)),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            textview.text = builder
        }

        fun showBottomSheetDialog(
            context: Context,
            locationList: List<String>,
            title: String?,
            callbackFromFragment: CommandCallbackWithValue
        ) {
            mBottomSheetDialog = BottomSheetDialog(context)
            val sheetView: View =
                LayoutInflater.from(context).inflate(R.layout.spinner_bottomsheet, null)
            (mBottomSheetDialog as BottomSheetDialog).setContentView(sheetView)
            (mBottomSheetDialog as BottomSheetDialog).setCancelable(true)
            (mBottomSheetDialog as BottomSheetDialog).show()
            val myAdapter = SpinnerAdapter(context, locationList, callbackFromFragment)
            val recyclerView: RecyclerView = sheetView.findViewById(R.id.chooseItemList)
            val chooseItemHeading = sheetView.findViewById<TextView>(R.id.chooseItemHeading)
            chooseItemHeading.text = title
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = myAdapter

        }

        fun showDialogWithCallback(
            context: Context,
            message: String,
            customMessage: String?,
            @DrawableRes resourceId: Int?,
            msgType:MessageType,
            commandCallback: CommandCallbackWithFailure
        ) {
            if (alertDialog != null && alertDialog!!.isShowing) {
                return
            }
            val builder = AlertDialog.Builder(context, R.style.full_screen_dialog)
            val inflater = LayoutInflater.from(context)
            @SuppressLint("InflateParams") val dialogView: View =
                inflater.inflate(R.layout.message_dialog, null)
            builder.setView(dialogView)
            builder.setCancelable(false)
            val btnOK = dialogView.findViewById<TextView>(R.id.buttonOk)
            val buttonCancel = dialogView.findViewById<TextView>(R.id.buttonCancel)
            val messageType = dialogView.findViewById<TextView>(R.id.messageType)
            val txt_msg = dialogView.findViewById<TextView>(R.id.txt_msg)
            val msg_txt_icon: AppCompatImageView = dialogView.findViewById(R.id.messageTypeIcon)
            txt_msg.text = message
            alertDialog = builder.create()
            alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            if (alertDialog!!.window != null) {
                alertDialog!!.window?.setLayout(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                alertDialog!!.window?.setBackgroundDrawableResource(R.color.transparent)
            }

            if (alertDialog!!.window != null) {
                alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                alertDialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            }
            messageType.setTextColor(getColor(context,R.color.colorPrimary))
            when (msgType) {
                MessageType.INFO -> {
                    messageType.visibility = View.GONE
                    msg_txt_icon.setImageDrawable(context.getDrawable(R.drawable.ic_info))
                    buttonCancel.visibility = View.VISIBLE
                    btnOK.visibility = View.VISIBLE
                }
                MessageType.QUESTION -> {
                    messageType.visibility = View.GONE
                    msg_txt_icon.setImageDrawable(context.getDrawable(R.drawable.ic_question))
                    buttonCancel.visibility = View.VISIBLE
                    btnOK.visibility = View.VISIBLE
                }
                MessageType.ERROR -> {
                    messageType.text = context.getResources().getString(R.string.error)
                    msg_txt_icon.setImageDrawable(context.getDrawable(R.drawable.ic_error_svg))
                    buttonCancel.visibility = View.GONE
                }
                MessageType.SUCCESS -> {
                    messageType.text = context.getResources().getString(R.string.success)
                    msg_txt_icon.setImageDrawable(context.getDrawable(R.drawable.ic_success))
                    buttonCancel.visibility = View.GONE
                }
                MessageType.BLOCKUSER -> {
                    messageType.text = context.getResources().getString(R.string.alert)
                    msg_txt_icon.setImageDrawable(context.getDrawable(R.drawable.ic_error_svg))
                    btnOK.visibility = View.GONE
                    buttonCancel.visibility = View.GONE
                }
                MessageType.CUSTOM -> {
                    messageType.text = customMessage
                    msg_txt_icon.setImageDrawable(resourceId?.let { context.getDrawable(it) })
                    alertMessage = message
//                if (message == context.getResources()
//                        .getString(R.string.dialog_polycy_content) || message == context.getResources()
//                        .getString(R.string.check_internet_message) || message == context.getResources()
//                        .getString(R.string.error_communication_message)
//                ) {
                    btnOK.visibility = View.VISIBLE
//                } else {
//                    btnOK.visibility = View.GONE
//                }
                    buttonCancel.visibility = View.GONE
                }
                MessageType.WARNING -> {
                    messageType.text = customMessage
                    msg_txt_icon.setImageDrawable(resourceId?.let { context.getDrawable(it) })
                    alertMessage = message
                    btnOK.visibility = View.VISIBLE
                    buttonCancel.visibility = View.VISIBLE
                }
            }

            btnOK.setOnClickListener { view: View? ->
                hideAlertDialog()
                commandCallback.onSuccess()
            }
            buttonCancel.setOnClickListener { v: View? ->
                hideAlertDialog()
                commandCallback.onFailure()
            }
            showAlertDialog()
        }
        fun hideAlertDialog() {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
        }
        private fun showAlertDialog() {
            if (alertDialog != null && !alertDialog!!.isShowing) {
                alertDialog!!.show()
            }
        }
    }






}