package com.gaps.champion11.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaps.champion11.databinding.FragmentBookingBinding
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.ui.BookingNumberActivity
import com.gaps.champion11.ui.adapter.NumberListAdapter
import com.gaps.champion11.ui.home.HomeFragment
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BookingFragment :Fragment() {
    private lateinit var bookingViewModel: BookingViewModel
    private var _binding: FragmentBookingBinding? = null
    private var disposableBookNow: Disposable? = null
    private val arr = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    var root: View? = null
    val numberList = ArrayList<NumberDetail>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {

        fun newInstance(): BookingFragment {
            return BookingFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as BookingNumberActivity?)!!.setHeaderTextBookingActivity()


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(root==null){
            bookingViewModel =
                ViewModelProvider(this).get(BookingViewModel::class.java)
            _binding = FragmentBookingBinding.inflate(inflater, container, false)
            root = binding.root
            setupListeners()
        }

        return root as View
    }

    private fun setupUI(){
        for (i in arr) {
            val numberDetail = NumberDetail(i, false);
            numberList.add(numberDetail)
        }
        val gridLayoutManager = GridLayoutManager(activity, 5, RecyclerView.VERTICAL, false)
        binding.numberList.layoutManager = gridLayoutManager

        val numberListAdapter = NumberListAdapter(numberList, context)
        binding.numberList.isNestedScrollingEnabled = false
        binding.numberList.adapter = numberListAdapter
    }

    private fun setupListeners(){
    numberList.clear()
    setupUI()
    disposableBookNow = binding.bookNow.clicks()
        .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            (activity as BookingNumberActivity?)!!.loadBookingNumberFragment()
        }

}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}