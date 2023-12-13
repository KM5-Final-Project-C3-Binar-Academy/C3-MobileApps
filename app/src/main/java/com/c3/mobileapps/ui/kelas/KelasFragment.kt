package com.c3.mobileapps.ui.kelas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryAdapter
import com.c3.mobileapps.databinding.FragmentKelasBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class KelasFragment : Fragment() {

    private lateinit var binding: FragmentKelasBinding

    private val kelasViewModel: KelasViewModel by inject()
    private lateinit var categoryCourseAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnFocusChangeListener{_, hasFocus ->
            if (hasFocus) {
                // Do something when the EditText is focused
                findNavController().navigate(R.id.searchFragment)
            }

        }


        setupRecyclerView()
        loadDataCategory()

        binding.lihatSemuaKategori.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("ModeView", true)

            findNavController().navigate(R.id.viewAllFragment, bundle)
        }

    }

    private fun loadDataCategory() {
        lifecycleScope.launch {
            kelasViewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                    categoryCourseAdapter.setData(database.first().categoryResponse.data)
                } else {
                    getCategory()
                }
            }
        }
    }

    private fun getCategory() {

        kelasViewModel.getListCategory()
        kelasViewModel.listCategory.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    it.data?.let {
                        categoryCourseAdapter.setData(it.data)
                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                    loadDataCategory()
                }

                Status.LOADING -> {

                }
            }

        }
    }

    private fun setupRecyclerView() {
        categoryCourseAdapter = CategoryAdapter(
            isAll = false,
            listener = {category ->
                val bundle = bundleOf("CATEGORY" to category)
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.kelasFragment, true)
                    .build()
                findNavController().navigate(R.id.courseFragment,bundle,navOptions)
            })
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategoryCourse.adapter = categoryCourseAdapter

    }


}