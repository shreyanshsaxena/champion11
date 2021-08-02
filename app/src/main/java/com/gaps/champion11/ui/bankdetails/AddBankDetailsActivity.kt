package com.gaps.champion11.ui.bankdetails

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityAddbankdetailsBinding
import com.gaps.champion11.model.BankDetailModel
import com.gaps.champion11.ui.BaseNavigationActivity

class AddBankDetailsActivity : BaseNavigationActivity() {
    private lateinit var binding: ActivityAddbankdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddbankdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger_white)
    }


    private fun saveBankDetails() {
        val bankDetailModel = BankDetailModel(binding.edtAccountNumber.text.toString()," "," ",binding.edtIfscCode.text.toString(),"",0,null)


    }
}