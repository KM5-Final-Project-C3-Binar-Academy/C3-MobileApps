package com.c3.mobileapps.ui.course

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentCourseBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var listCourseAdapter: ListCourseAdapter
    private val courseViewModel: CourseViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        checkMode()
        loadDataList()
        setupRvCategory()
        return binding.root
    }
    private fun checkMode(){
        binding.cpAll.setOnClickListener {
            binding.cpKelasPremium.isChecked = false
            binding.cpKelasGratis.isChecked = false
            courseViewModel.setMode("All")
        }
        binding.cpKelasPremium.setOnClickListener {
            binding.cpAll.isChecked = false
            binding.cpKelasGratis.isChecked = false
            courseViewModel.setMode(true)
        }

        binding.cpKelasGratis.setOnClickListener {
            binding.cpKelasPremium.isChecked = false
            binding.cpAll.isChecked = false
            courseViewModel.setMode(false)
        }
    }

    private fun coursePerMode(listCourse: List<Course>){
        courseViewModel.mode.observe(viewLifecycleOwner){mode ->
            when(mode){
                "All" -> {
                    Log.d("cek mode",mode.toString())
                    checkMode()
                    listCourseAdapter.setData(listCourse)
                    binding.progressBarMenu.isVisible = false
                }
                true -> {
                    Log.d("cek mode",mode.toString())
                    checkMode()

                    val premiumList = mutableListOf<Course>()

                    listCourse.forEach {
                        if (it.premium == true){
                            premiumList.add(it)
                        }
                    }
                    listCourseAdapter.setData(premiumList)
                    binding.progressBarMenu.isVisible = false

                }
                false ->{
                    Log.d("cek mode",mode.toString())
                    checkMode()

                    val gratisList = mutableListOf<Course>()

                    listCourse.forEach {
                        if (it.premium == false){
                            gratisList.add(it)
                        }
                    }
                    listCourseAdapter.setData(gratisList)
                    binding.progressBarMenu.isVisible = false
                }
            }
        }
    }

    private fun loadDataList() {
        Log.d("dataMenu", "no connection, list course view from database")
        lifecycleScope.launch {
            courseViewModel.readMenu.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    coursePerMode(database.first().courseResponse.data)
                } else {
                    remoteGetCourse()
                }
            }
        }
    }

    private fun remoteGetCourse(){
        courseViewModel.getListCourse()
        courseViewModel.listCourse.observe(viewLifecycleOwner){it ->
            when (it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data", Gson().toJson(it.data))
                    binding.progressBarMenu.isVisible = false
                    it.data?.let { listCourseAdapter.setData(it.data) }
                }
                Status.ERROR -> {
                    Log.e("Cek Data", it.message.toString())
                    binding.progressBarMenu.isVisible = false
                    loadDataList()

                }
                Status.LOADING -> {
                    binding.progressBarMenu.isVisible = true
                }
            }
        }

    }

    private fun setupRvCategory(){
        listCourseAdapter = ListCourseAdapter(emptyList())
        binding.rvCourse.setHasFixedSize(true)
        binding.rvCourse.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCourse.adapter = listCourseAdapter
    }


}