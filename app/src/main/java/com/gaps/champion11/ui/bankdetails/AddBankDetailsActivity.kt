package com.gaps.champion11.ui.bankdetails

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityAddbankdetailsBinding
import com.gaps.champion11.enum.MessageType
import com.gaps.champion11.model.BankDetailModel
import com.gaps.champion11.model.GamesModel
import com.gaps.champion11.retrofit.RetrofitApiClient
import com.gaps.champion11.ui.BaseNavigationActivity
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.utils.CommandCallbackWithFailure
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class AddBankDetailsActivity : BaseNavigationActivity() {
    private var bankDetailId: Int=0
    private var bankDetailsExist: Boolean=false
    private lateinit var binding: ActivityAddbankdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddbankdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger)
        getBankDetailsFromServer()
        setupListeners()
    }

    private fun setupListeners() {
       val disposableResendOTP = binding.saveBankDetails.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                saveBankDetails()
            }
    }


    private fun sendDetailsToTheServer() {

        val bankDetailModel = BankDetailModel(
            binding.edtAccountNumber.text.toString(),
            "SBI Bank",
            "Noida Sector 18",
            binding.edtIfscCode.text.toString(),
            "Plot No 3A, Tower 8, Noida",
            bankDetailId,
            binding.edtupiTxt.text.toString()
        )

        if(bankDetailsExist&& bankDetailId!=0){
            val call =
                RetrofitApiClient.getApiInterfaceUser(context).updateUserBankDetails(bankDetailId,bankDetailModel)
            call!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>?,
                    response: Response<ResponseBody?>?
                ) {
                    if (response != null && response.isSuccessful) {
                        showSuccessDialog("Bank details updated successfully")
                    } else {
                        showErrorDialog("Error in updating bank details")
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                    showErrorDialog("Error in updating bank details")
                }


            })
        }
        else{
            val call =
                RetrofitApiClient.getApiInterfaceUser(context).saveUserBankDetails(bankDetailModel)
            call!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>?,
                    response: Response<ResponseBody?>?
                ) {
                    if (response != null && response.isSuccessful) {
                        showSuccessDialog("Bank details saved successfully")
                    } else {
                        showErrorDialog("Error in saving bank details")
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                    showErrorDialog("Error in saving bank details")
                }


            })
        }

    }


    private fun getBankDetailsFromServer() {
        showProgressDialog(context)
        val call =
            RetrofitApiClient.getApiInterfaceUser(context).getBankDetails()
        call!!.enqueue(object : Callback<List<BankDetailModel>?> {
            override fun onResponse(
                call: Call<List<BankDetailModel>?>?,
                response: Response<List<BankDetailModel>?>?
            ) {
                hideProgressDialog()
                if (response != null && response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                    bankDetailsExist=true
                    bankDetailId=response.body()!![0].id
                    binding.edtAccountNumber.setText(response.body()!![0].accountNo)
                    binding.edtupiTxt.setText(response.body()!![0].upiId)
                    binding.edtIfscCode.setText(response.body()!![0].ifsc)
                }

            }

            override fun onFailure(call: Call<List<BankDetailModel>?>?, t: Throwable?) {
                hideProgressDialog()
            }


        })
    }

    fun showErrorDialog(message: String) {
        AppUtil.showDialogWithCallback(
            context = this@AddBankDetailsActivity,
            message,
            null,
            null,
            MessageType.ERROR,
            object : CommandCallbackWithFailure {
                override fun onSuccess() {
                    AppUtil.hideAlertDialog()
                }
            })
    }

    fun showSuccessDialog(message: String) {
        AppUtil.showDialogWithCallback(
            context = this@AddBankDetailsActivity,
            message,
            null,
            null,
            MessageType.SUCCESS,
            object : CommandCallbackWithFailure {
                override fun onSuccess() {
                    AppUtil.hideAlertDialog()
                }
            })
    }

    fun showSnackBar(message: String) {
        AppUtil.onSnackCoordinate(
            binding.edtAccountHolderName,
            message
        )
    }

    private fun saveBankDetails() {
        AppUtil.hideKeyboard(context)
        //check upi id
        if (binding.edtupiTxt.text.toString().equals("")) {
            if (binding.edtIfscCode.text.toString()
                    .equals("") || binding.edtAccountNumber.text.toString()
                    .equals("") || binding.edtAccountHolderName.text.toString().equals("")
            ) {
                showSnackBar("Please enter complete bank details to save")
                return
            } else {
                sendDetailsToTheServer()
            }
        } else {
            if (!binding.edtupiTxt.text.toString().contains("@")) {
                showSnackBar("Please enter a valid UPI id")
                return
            } else {
                if (binding.edtIfscCode.text.toString()
                        .equals("") && binding.edtAccountNumber.text.toString()
                        .equals("") && binding.edtAccountHolderName.text.toString().equals("")
                ) {
                    sendDetailsToTheServer()
                } else if (binding.edtIfscCode.text.toString()
                        .equals("") || binding.edtAccountNumber.text.toString()
                        .equals("") || binding.edtAccountHolderName.text.toString().equals("")
                ) {
                    showSnackBar("Please enter complete bank details to save")
                    return
                } else {
                    sendDetailsToTheServer()
                    return
                }
            }

        }


    }
}