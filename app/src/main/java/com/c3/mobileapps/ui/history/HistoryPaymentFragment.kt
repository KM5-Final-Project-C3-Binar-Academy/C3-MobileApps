package com.c3.mobileapps.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.HistoryAdapter
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.databinding.FragmentHistoryPaymentBinding
import com.c3.mobileapps.ui.course.FIlterBottomSheet
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.utils.Status
import org.koin.android.ext.android.inject


class HistoryPaymentFragment : Fragment() {
    private lateinit var binding: FragmentHistoryPaymentBinding
    private val historyViewModel: HistoryViewModel by inject()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding = FragmentHistoryPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        getHistory()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getHistory(){
        historyViewModel.getListPayment()
        historyViewModel.paymentResp.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let {resp ->
                        if (resp.data.isNotEmpty()){
                            historyAdapter.setData(resp.data)
                            showRecyclerView()
                        }else{
                            binding.rvHistory.visibility = View.GONE
                            binding.emptyData.visibility = View.VISIBLE
                            binding.shimmerFrameLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                        }
                    }
                }
                Status.LOADING ->{
                    binding.shimmerFrameLayout.startShimmer()

                }

                Status.ERROR ->{
                    binding.shimmerFrameLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun showRecyclerView() {
        binding.shimmerFrameLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.emptyData.visibility = View.GONE
        binding.rvHistory.visibility = View.VISIBLE
    }

    private fun setRecyclerView() {
        historyAdapter = HistoryAdapter(emptyList(), listener = { pickItem ->

            Log.e("History",pickItem.status.toString())
            if (pickItem.status != "COMPLETED"){
                val bundle = bundleOf("PAYMENT" to pickItem)
                findNavController().navigate(R.id.confirmPaymentFragment, bundle)
            }

        })

        binding.rvHistory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvHistory.adapter = historyAdapter
    }

}