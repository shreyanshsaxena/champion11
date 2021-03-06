package com.gaps.champion11.utils

import GameDetailAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.R
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.model.UserStatsResponse
import com.gaps.champion11.ui.adapter.GameHistoryAdapter
import com.gaps.champion11.ui.adapter.NumberListAdapter
import com.gaps.champion11.ui.adapter.SpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import org.joda.time.DateTime
import java.text.MessageFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AppUtil {

    companion object {
        private var gdprDialog: AlertDialog? = null
        private var gameDetailDialog: AlertDialog? = null

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

        fun getDateTimeFromString(s: String?): String {
            val pattern = "yyyy-MM-dd'T'HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
            var d: Date? = null
            try {
                d = simpleDateFormat.parse(s)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return DateTime(d).toString("hh:mm a")
        }

        fun getDateFromString(s: String?): String {
            val pattern = "yyyy-MM-dd'T'HH:mm:ss"
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
            var d: Date? = null
            try {
                d = simpleDateFormat.parse(s)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return DateTime(d).toString("dd MMM yyyy")
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

        fun getGenderValueString(genderVal: Int?): String {
            if (genderVal == 1)
                return "Male"
            else
                return "Female"
        }

        fun showDialogWithCallback(
            context: Context,
            message: String,
            customMessage: String?,
            @DrawableRes resourceId: Int?,
            msgType: MessageType,
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
            messageType.setTextColor(getColor(context, R.color.colorPrimary))
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
                    messageType.setTextColor(getColor(context, R.color.colorPrimary))
                    msg_txt_icon.setImageDrawable(context.getDrawable(R.drawable.ic_error_svg))
                    buttonCancel.visibility = View.GONE
                }
                MessageType.SUCCESS -> {
                    messageType.text = context.getResources().getString(R.string.success)
                    messageType.setTextColor(getColor(context, R.color.colorGreen))
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
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(alertDialog!!.window!!.attributes)
            layoutParams.width = (getDeviceMetrics(context).widthPixels * 0.90).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

            alertDialog!!.window!!.attributes = layoutParams
        }

        fun showGameDetailDialogWithCallback(
            context: Context,
            numberList: List<NumberDetail>,
            gameDataItem: UserStatsResponse
        ) {
            if (gameDetailDialog != null && gameDetailDialog!!.isShowing()) {
                return
            }
            val rootView =
                (context as Activity).window.decorView.findViewById<View>(android.R.id.content)
            val builder = AlertDialog.Builder(context, R.style.full_screen_dialog)
            val inflater = LayoutInflater.from(context)
            @SuppressLint("InflateParams") val dialogView: View =
                inflater.inflate(R.layout.game_bet_detail_dialog, null)
            builder.setView(dialogView)
            builder.setCancelable(true)
            val numberListRecycler = dialogView.findViewById<RecyclerView>(R.id.numberList)
            val gameDate = dialogView.findViewById<TextView>(R.id.gameDate)
            val gameSlotTime = dialogView.findViewById<TextView>(R.id.gameSlotTime)
            val betAmount = dialogView.findViewById<TextView>(R.id.betAmt)
            val winLoseAmount = dialogView.findViewById<TextView>(R.id.winLoseAmt)
            val winLoseAmt = gameDataItem.resultAmount - gameDataItem.userGameAmount
            val prefix:String
            if (winLoseAmt > 0) {
                winLoseAmount.setTextColor(context.resources.getColor(R.color.colorGreen))

            } else {
                winLoseAmount.setTextColor(context.resources.getColor(R.color.colorPrimary))
            }
            gameSlotTime.text =
                getDateTimeFromString(gameDataItem.startTime).uppercase() + " to " + AppUtil.getDateTimeFromString(
                    gameDataItem.endTime
                ).uppercase()
            gameDate.text = getDateFromString(gameDataItem.startTime)
            betAmount.text = gameDataItem.userGameAmount.toString()
            winLoseAmount.text=MessageFormat.format("{0}",winLoseAmt)
            val gridLayoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            numberListRecycler.layoutManager = gridLayoutManager

            val numberListAdapter = GameDetailAdapter(numberList, gameDataItem, context)
            numberListRecycler.isNestedScrollingEnabled = false
            numberListRecycler.adapter = numberListAdapter

            gameDetailDialog = builder.create()
            gameDetailDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            if (gameDetailDialog!!.window != null) {
                gameDetailDialog!!.window?.setLayout(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                gameDetailDialog!!.window!!.setBackgroundDrawableResource(android.R.color.white)
            }
            if (gameDetailDialog!!.window != null) {
                gameDetailDialog!!.window
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                gameDetailDialog!!.window
                    ?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            }
//            btnOK.setOnClickListener { view: View? ->
//                hideTncAlertDialog()
//                commandCallback.onSuccess()
//            }
//            buttonCancel.setOnClickListener { v: View? ->
//                hideTncAlertDialog()
//                commandCallback.onFailure()
//            }
            showGameDetailAlertDialog()
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(gameDetailDialog!!.window!!.attributes)
            layoutParams.width = (getDeviceMetrics(context).widthPixels * 0.90).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

            gameDetailDialog!!.window!!.attributes = layoutParams

        }

        fun hideAlertDialog() {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
        }

        fun hideTncAlertDialog() {
            if (gdprDialog != null && gdprDialog!!.isShowing) {
                gdprDialog!!.dismiss()
            }
        }

        fun getDeviceMetrics(context: Context): DisplayMetrics {
            val metrics = DisplayMetrics()
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            display.getMetrics(metrics)
            return metrics
        }

        private fun showAlertDialog() {
            if (alertDialog != null && !alertDialog!!.isShowing) {
                alertDialog!!.show()
            }
        }

        private fun showTncAlertDialog() {
            if (gdprDialog != null && !gdprDialog!!.isShowing) {
                gdprDialog!!.show()
            }
        }

        private fun showGameDetailAlertDialog() {
            if (gameDetailDialog != null && !gameDetailDialog!!.isShowing) {
                gameDetailDialog!!.show()
            }
        }

        private fun hideGameDetailAlertDialog() {
            if (gameDetailDialog != null && gameDetailDialog!!.isShowing) {
                gameDetailDialog!!.hide()
            }
        }

        fun hideKeyboard(ctx: Context?) {
            if (ctx == null) return
            val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // check if no view has focus:
            val v = (ctx as Activity).currentFocus ?: return
            inputManager.hideSoftInputFromWindow(v.windowToken, 0)
        }

        fun onSnackCoordinate(view: View, errorMessage: String) {
            //Snackbar(view)
            val snackbar = Snackbar.make(
                view, errorMessage,
                Snackbar.LENGTH_LONG
            )
            snackbar.setActionTextColor(Color.RED)
            val snackbarView = snackbar.view
            val params: CoordinatorLayout.LayoutParams =
                snackbarView.layoutParams as CoordinatorLayout.LayoutParams
            params.setMargins(
                0,
                0,
                0,
                0
            )
            snackbarView.layoutParams = params
            snackbarView.setBackgroundColor(Color.RED)
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 16f
            snackbar.show()
        }

        fun onSnack(view: View, errorMessage: String) {
            //Snackbar(view)
            val snackbar = Snackbar.make(
                view, errorMessage,
                Snackbar.LENGTH_LONG
            )
            snackbar.setActionTextColor(Color.RED)
            val snackbarView = snackbar.view
            val params: FrameLayout.LayoutParams =
                snackbarView.layoutParams as FrameLayout.LayoutParams
            params.setMargins(
                0,
                0,
                0,
                0
            );
            snackbarView.layoutParams = params
            snackbarView.setBackgroundColor(Color.RED)
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 16f
            snackbar.show()
        }

        fun showTncDialogWithCallback(
            context: Context,
            commandCallback: CommandCallbackWithFailure
        ) {
            if (gdprDialog != null && gdprDialog!!.isShowing()) {
                return
            }
            val rootView =
                (context as Activity).window.decorView.findViewById<View>(android.R.id.content)
            val builder = AlertDialog.Builder(context, R.style.full_screen_dialog)
            val inflater = LayoutInflater.from(context)
            @SuppressLint("InflateParams") val dialogView: View =
                inflater.inflate(R.layout.tncacceptdialog, null)
            builder.setView(dialogView)
            builder.setCancelable(false)
            val btnOK = dialogView.findViewById<TextView>(R.id.buttonAccept)
            val buttonCancel = dialogView.findViewById<TextView>(R.id.buttonDecline)
            val imageBack = dialogView.findViewById<ImageView>(R.id.imageBack)
            val WebViewWithCSS = dialogView.findViewById<WebView>(R.id.web_view)
            gdprDialog = builder.create()
            gdprDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            if (gdprDialog!!.getWindow() != null) {
                gdprDialog!!.getWindow()?.setLayout(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                gdprDialog!!.getWindow()!!
                    .setBackgroundDrawableResource(android.R.color.transparent)
            }
            if (gdprDialog!!.getWindow() != null) {
                gdprDialog!!.getWindow()
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                gdprDialog!!.getWindow()
                    ?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            }
            btnOK.setOnClickListener { view: View? ->
                hideTncAlertDialog()
                commandCallback.onSuccess()
            }
            buttonCancel.setOnClickListener { v: View? ->
                hideTncAlertDialog()
                commandCallback.onFailure()
            }
            showTncAlertDialog()
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(gdprDialog!!.window!!.attributes)
            layoutParams.width = (getDeviceMetrics(context).widthPixels * 0.90).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

            gdprDialog!!.window!!.attributes = layoutParams
            val webSetting = WebViewWithCSS.settings
            webSetting.javaScriptEnabled = true
            webSetting.allowFileAccess = true
            webSetting.allowContentAccess = true
            WebViewWithCSS.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            WebViewWithCSS.isScrollbarFadingEnabled = false
            WebViewWithCSS.webViewClient = WebViewClient()
            WebViewWithCSS.loadUrl("file:///android_asset/gdpr")
        }

    }


}