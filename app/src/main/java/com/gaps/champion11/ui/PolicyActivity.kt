package com.gaps.champion11.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.gaps.champion11.R
import com.gaps.champion11.databinding.ActivityPolicyBinding
import com.hcl.iatm.Utils.StringConstants

class PolicyActivity : BaseNavigationActivity() {
    private lateinit var binding: ActivityPolicyBinding
    var displayContentType: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayContentType = intent.getStringExtra(StringConstants.DISPLAY_CONTENT_TYPE).toString()
        binding = ActivityPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = binding.toolbar
        init(this, toolbar, R.drawable.ic_hamburger)
        when (displayContentType) {
            "tnc" -> {
                binding.tnc.root.visibility = View.VISIBLE
                binding.privacy.root.visibility = View.GONE
                binding.about.root.visibility = View.GONE

            }
            "about" -> {
                binding.tnc.root.visibility = View.GONE
                binding.privacy.root.visibility = View.GONE
                binding.about.root.visibility = View.VISIBLE


            }
            "privacy" -> {
                binding.tnc.root.visibility = View.GONE
                binding.privacy.root.visibility = View.VISIBLE
                binding.about.root.visibility = View.GONE


            }
        }
        setupListeners()


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            displayContentType = intent.getStringExtra(StringConstants.DISPLAY_CONTENT_TYPE).toString()
            when (displayContentType) {
                "tnc" -> {
                    binding.tnc.root.visibility = View.VISIBLE
                    binding.privacy.root.visibility = View.GONE
                    binding.about.root.visibility = View.GONE

                }
                "about" -> {
                    binding.tnc.root.visibility = View.GONE
                    binding.privacy.root.visibility = View.GONE
                    binding.about.root.visibility = View.VISIBLE


                }
                "privacy" -> {
                    binding.tnc.root.visibility = View.GONE
                    binding.privacy.root.visibility = View.VISIBLE
                    binding.about.root.visibility = View.GONE


                }
            }
        }


    }
    private fun setupListeners() {

    }
}