package com.c3.mobileapps.ui.notification

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.NotifAdapter
import com.c3.mobileapps.data.remote.model.response.notification.Notification
import com.c3.mobileapps.databinding.FragmentNotificationBinding
import com.c3.mobileapps.ui.main_activity.MainViewModel
import com.c3.mobileapps.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    private val notificationViewModel: NotificationViewModel by inject()
    private val mainViewModel: MainViewModel by inject()
    private lateinit var notifAdapter: NotifAdapter
    private var isDataLoading = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        getNotification()
    }

    private fun readAllNotif() {
        binding.readAll.setOnClickListener {
            if (!isDataLoading) {
                isDataLoading = true
                notificationViewModel.readAllNotif()
                notificationViewModel.notifResp.observe(viewLifecycleOwner) {

                    when (it.status) {
                        Status.SUCCESS -> {
                            notificationViewModel.notifResp.removeObservers(viewLifecycleOwner)
                            getNotification()

                            val bottomNavigationView: BottomNavigationView? =
                                activity?.findViewById(R.id.bottom_navigation)
                            bottomNavigationView?.removeBadge(R.id.notificationFragment)

                            Log.e("Notif", it.data?.data?.size.toString())
                            isDataLoading = false

                        }

                        Status.LOADING -> {
                            binding.shimmerFrameLayout.startShimmer()
                            isDataLoading = false
                        }

                        Status.ERROR -> {
                            binding.shimmerFrameLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            binding.emptyData.visibility = View.VISIBLE
                            isDataLoading = false
                        }
                    }
                }
            }
        }
    }

    private fun getNotification() {
        notificationViewModel.getListNotif()
        notificationViewModel.notifResp.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { resp ->
                        if (resp.data.isNotEmpty()) {
                            notifAdapter.setData(resp.data)
                            swipeToDelete(resp.data)
                            showRecyclerView()
                            readAllNotif()

                            val unViewed = resp.data.count {notif ->
                                notif.viewed == false
                            }
                            val bottomNavigationView: BottomNavigationView? =
                                activity?.findViewById(R.id.bottom_navigation)
                            val badge = bottomNavigationView?.getOrCreateBadge(R.id.notificationFragment)

                            if (unViewed != 0){
                                badge?.number = unViewed
                            }else{
                                bottomNavigationView?.removeBadge(R.id.notificationFragment)

                            }
                        } else {
                            binding.rvNotif.visibility = View.GONE
                            binding.emptyData.visibility = View.VISIBLE
                            binding.shimmerFrameLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                        }
                    }

                }

                Status.LOADING -> {
                    binding.shimmerFrameLayout.startShimmer()
                }

                Status.ERROR -> {
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
        notifAdapter = NotifAdapter(emptyList(), listener = {
            if (!isDataLoading) {
                isDataLoading = true
                Log.e("Clicked", it.toString())
                notificationViewModel.readNotif(it.id!!)
                notificationViewModel.notifIdResp.observe(viewLifecycleOwner) { readNotifResponse ->
                    when (readNotifResponse.status) {
                        Status.SUCCESS -> {
                            val bottomNavigationView: BottomNavigationView? =
                                activity?.findViewById(R.id.bottom_navigation)
                            val badge = bottomNavigationView?.getBadge(R.id.notificationFragment)

                            Log.e("Clicked", readNotifResponse.toString())
                            getNotification()
                            isDataLoading = false
                            notificationViewModel.notifIdResp.removeObservers(viewLifecycleOwner)
                            if (badge?.number != 0){
                                badge?.number = badge?.number?.minus(1)!!
                            }else{
                                bottomNavigationView.removeBadge(R.id.notificationFragment)
                            }

                        }

                        Status.LOADING -> {
                            binding.shimmerFrameLayout.startShimmer()
                            isDataLoading = false
                        }

                        Status.ERROR -> {
                            binding.shimmerFrameLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            binding.emptyData.visibility = View.VISIBLE
                            isDataLoading = false
                        }
                    }
                }
            }
        })

        binding.rvNotif.apply {
            adapter = notifAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }


    }

    private fun swipeToDelete(data: List<Notification>) {
        val icon: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
        val background = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.red))

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedData = data[viewHolder.adapterPosition]
                val afterDeletedList = ArrayList(data)
                val originalData = ArrayList(data)

                afterDeletedList.removeAt(viewHolder.adapterPosition)
                notifAdapter.setData(afterDeletedList)

                notificationViewModel.deleteNotif(deletedData.id!!)
                notificationViewModel.notifIdResp.observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Snackbar.make(
                                binding.root,
                                "Notifikasi Berhasil DIhapus",
                                Snackbar.LENGTH_SHORT
                            )
                                .setBackgroundTint(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.primary
                                    )
                                )
                                .setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                .show()
                            getNotification()
                            notificationViewModel.notifIdResp.removeObservers(viewLifecycleOwner)


                        }

                        Status.LOADING -> {
                        }

                        Status.ERROR -> {
                            notifAdapter.setData(originalData)
                            // Handle error state if needed
                            Snackbar.make(
                                binding.root,
                                "Terjadi kesalahan saat menghapus notifikasi",
                                Snackbar.LENGTH_SHORT
                            )
                                .setBackgroundTint(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.red
                                    )
                                )
                                .setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                                .show()

                        }
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.height

                // Draw background
                background.setBounds(
                    (itemView.right + dX).toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                // Draw the delete icon
                val iconMargin = (itemHeight - icon!!.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemHeight - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight

                // Draw the delete icon only when actualDx is less than maxDx
                if (dX < 0) {
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(binding.rvNotif)
    }

    private val Int.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics
        ).roundToInt()
}