package com.c3.mobileapps.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val listItem = mutableListOf<filterCategory>()
//        listItem.add(filterCategory("UI/UX Design"))
//        listItem.add(filterCategory("Product Management"))
//        listItem.add(filterCategory("Web Development"))
//        listItem.add(filterCategory("Android Development"))
//        listItem.add(filterCategory("AI Development"))
//        listItem.add(filterCategory("Business Intelligent"))
//        listItem.add(filterCategory("Fullstack Development"))
//        listItem.add(filterCategory("Data Science"))
//        listItem.add(filterCategory("Cyber security"))
//        listItem.add(filterCategory("Mobile App Development"))
//        listItem.add(filterCategory("Game Development"))
//        listItem.add(filterCategory("Cloud Computing"))
//        listItem.add(filterCategory("Machine Learning"))
//        listItem.add(filterCategory("DevOps"))
//        listItem.add(filterCategory("Blockchain"))

//		val adapter = ItemFilterAdapter(listItem)
//		binding.rvViewAll.adapter = adapter
//		binding.rvViewAll.layoutManager = GridLayoutManager(requireActivity(), 2)
//
//        binding.tvBottomSheet.setOnClickListener {
//            val bottomSheet = BottomSheet()
//            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
//            bottomSheet.show(fragmentManager, bottomSheet.tag)
//        }
    }

}