package com.gaps.champion11.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gaps.champion11.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


abstract class BaseActivity : AppCompatActivity() {
    var progressDialog: AlertDialog? = null
    var dialog:Dialog?=null

    class ProgressDialog {
        companion object {
            @SuppressLint("InflateParams")
            fun progressDialog(context: Context): Dialog{
                val dialog = Dialog(context)
                val inflate:View  = LayoutInflater.from(context).inflate(R.layout.loader, null)
                dialog.setCancelable(false)
                dialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                )
                dialog.setContentView(inflate)
                return dialog
            }
        }
    }
    fun showProgressDialog(context: Context){
        if(dialog==null){
            dialog = ProgressDialog.progressDialog(context)
        }
        if(!dialog!!.isShowing){
            dialog!!.show()
        }

    }
    fun hideProgressDialog(){
        if(dialog!=null&&dialog!!.isShowing){
            dialog!!.hide()
        }
    }
    fun TextInputEditText.isNotNullOrEmpty(errorString: String): Boolean {
        val textInputLayout = this.parent.parent as TextInputLayout
        textInputLayout.errorIconDrawable = null
        this.onChange { textInputLayout.error = null }

        return if (this.text.toString().trim().isEmpty()) {
            textInputLayout.error = errorString
            false
        } else {
            true
        }
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    fun carbon.widget.EditText.isNotNullOrEmpty(view: View, errorString: String): Boolean {

        return if (this.text.toString().trim().isBlank()) {
            hideKeyboard()
            onSnack(view, errorString)
            false
        } else {
            true
        }
    }

    fun carbon.widget.EditText.isValidMobileNumber(view: View, errorString: String): Boolean {

        return if (this.text.toString().length < 10) {
            hideKeyboard()
            onSnack(view, errorString)
            false
        } else {
            true
        }
    }

    fun onSnack(view: View, errorMessage: String) {
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, errorMessage,
            Snackbar.LENGTH_LONG
        )
        snackbar.setActionTextColor(Color.RED)
        val snackbarView = snackbar.view
        val params: FrameLayout.LayoutParams = snackbarView.layoutParams as FrameLayout.LayoutParams
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

    fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}