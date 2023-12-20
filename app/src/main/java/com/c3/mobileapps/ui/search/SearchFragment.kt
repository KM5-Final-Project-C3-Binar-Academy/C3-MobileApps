package com.c3.mobileapps.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.ListCourseAdapter
import com.c3.mobileapps.databinding.FragmentSearchBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by inject()
    private lateinit var listCourseAdapter: ListCourseAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupRvCourse()
        binding.etSearch.requestFocus()
        binding.btnSearch.setOnClickListener {
            hideKeyboardAndClearFocus()
            searchCourse(binding.etSearch.text.toString())

        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun hideKeyboardAndClearFocus() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if the currently focused view is an EditText or has focusable property
        if (requireActivity().currentFocus is View) {
            val focusedView = requireActivity().currentFocus as View
            focusedView.clearFocus()
        }

        // Hide the keyboard
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun searchCourse(
        search: String?
    ) {
        searchViewModel.getSearchCourse(search)
        searchViewModel.resultSearch.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    listCourseAdapter.clearData()
                    Log.e("Cek Data Course", Gson().toJson(it.data))
                    it.data?.let { listCourseAdapter.setData(it.data) }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Course", it.message.toString())

                }

                Status.LOADING -> {
                }
            }
        }
    }

    private fun setupRvCourse() {
        listCourseAdapter = ListCourseAdapter(emptyList(), listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.action_courseFragment_to_detailCourseFragment, bundle)
        })

        binding.rvSearch.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSearch.adapter = listCourseAdapter
    }



}