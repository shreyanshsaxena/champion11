package com.gaps.champion11.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gaps.champion11.databinding.FragmentBookingNumberBinding
import com.gaps.champion11.model.NumberDetail
import com.gaps.champion11.utils.SharedPrefUtils

class BookNumberFragment : Fragment() {
    private lateinit var bookingViewModel: BookingViewModel
    private var _binding: FragmentBookingNumberBinding? = null
    val numberList = ArrayList<NumberDetail>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {

        fun newInstance(): BookNumberFragment {
            return BookNumberFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookingViewModel =
            ViewModelProvider(this).get(BookingViewModel::class.java)
        _binding = FragmentBookingNumberBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupListeners()
//        RetrofitApiClient.getApiInterfaceAsset(context)

        return root
    }
    fun setupListeners(){
        binding.luckyNo.text=
            context?.let { SharedPrefUtils.getString(it,SharedPrefUtils.SELECTED_NO,null) }
//        disposableBookNow = binding.bookNow.clicks()
//            .observeOn(Schedulers.io()).throttleFirst(1, TimeUnit.SECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
////                startActivity(Intent(this@Boo, HomeScreenAcivity::class.java))
//
//            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}