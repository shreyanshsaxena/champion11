package com.gaps.champion11.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gaps.champion11.HomeScreenActivity
import com.gaps.champion11.utils.AppUtil
import com.gaps.champion11.databinding.FragmentHomeBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var disposableLogin: Disposable? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.currentSlotTime
        setupListeners()
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        AppUtil.setSpnnableStringFormat(binding.twentyfourseven,context)
        return root
    }

    private fun setupListeners() {
            disposableLogin = binding.bookCurrentSlot.clicks()
            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (activity as HomeScreenActivity).callBookingActivity()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}