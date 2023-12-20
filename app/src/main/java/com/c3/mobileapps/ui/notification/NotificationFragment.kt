package com.c3.mobileapps.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.NotifAdapter
import com.c3.mobileapps.databinding.FragmentNotificationBinding
import com.c3.mobileapps.utils.Status
import org.koin.android.ext.android.inject

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    private val notificationViewModel: NotificationViewModel by inject()
    private lateinit var notifAdapter: NotifAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        getNotification()
    }

    private fun getNotification(){
        notificationViewModel.getListNotif()
        notificationViewModel.notifResp.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let {resp ->
                        if (resp.data.isNotEmpty()){
                            notifAdapter.setData(resp.data)
                            showRecyclerView()
                        }else{
                            binding.rvNotif.visibility = View.GONE
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
                    binding.emptyData.visibility = View.VISIBLE
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
        binding.rvNotif.visibility = View.VISIBLE
    }

    private fun setRecyclerView() {

        notifAdapter = NotifAdapter(emptyList(), listener = null)

        binding.rvNotif.apply {
            adapter = notifAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}