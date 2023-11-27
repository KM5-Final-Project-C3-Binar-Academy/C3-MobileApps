package com.c3.mobileapps.ui.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.data.remote.model.APIClient
import com.c3.mobileapps.databinding.FragmentCourseBinding
import com.c3.mobileapps.util.CourseViewModelFactory
import com.c3.mobileapps.util.Status
import com.google.gson.Gson

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var  listCourseAdapter: ListCourseAdapter
    private lateinit var viewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, CourseViewModelFactory(APIClient.endpointAPIService))[CourseViewModel::class.java]
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listCourseAdapter = ListCourseAdapter(this@CourseFragment, arrayListOf() )
        binding.rvCourse.setHasFixedSize(true)
        binding.rvCourse.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCourse.adapter = listCourseAdapter
        remoteGetList()

    }
    @SuppressLint("FragmentLiveDataObserve")
    fun remoteGetList(){
        viewModel.getAllCategory().observe(this){
            when (it.status){
                Status.SUCCESS -> {
                    Log.e("SimpleNetworking", Gson().toJson(it.data))
                    binding.progressBarMenu.isVisible = false
                    it.data?.data?.let { it1 -> listCourseAdapter.setData(it1) }
                }
                Status.ERROR -> {
                    binding.progressBarMenu.isVisible = false
                    Log.e("SimpleNetworking", it.message.toString())
                }
                Status.LOADING -> {
                    binding.progressBarMenu.isVisible = true
                }
            }
        }
    }


}