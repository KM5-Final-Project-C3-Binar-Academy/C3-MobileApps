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
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryAdapter
import com.c3.mobileapps.adapters.KelasAdapter
import com.c3.mobileapps.databinding.FragmentKelasBinding
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class KelasFragment : Fragment() {

    private lateinit var binding: FragmentKelasBinding

    private val kelasViewModel: KelasViewModel by inject()
    private lateinit var categoryCourseAdapter: CategoryAdapter
    private lateinit var kelasAdapter: KelasAdapter

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

        kelasViewModel.isLogin.observe(viewLifecycleOwner){
            if (it){

                setupRecyclerView()
                loadDataCategory()
                kelasViewModel.typeFilter.observe(viewLifecycleOwner){
                    Log.e("KELAS",it.toString())
                    checkMode()
                    getKelasUser(it)
                }

                binding.lihatSemuaKategori.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putBoolean("ModeView", true)

                    findNavController().navigate(R.id.viewAllFragment, bundle)
                }

                binding.expandKursusPopuler.setOnClickListener {
                    findNavController().navigate(R.id.viewAllKelasFragment)
                }
            }else{
                val nonLoginBottomSheet = NonLoginBottomSheet(R.id.kelasFragment)
                nonLoginBottomSheet.isCancelable = false
                nonLoginBottomSheet.show(childFragmentManager, nonLoginBottomSheet.tag)
            }
        }
    }

    private fun checkMode() {
        binding.cpAll.setOnClickListener {
            binding.cpInProgress.isChecked = false
            binding.cpSelesai.isChecked = false
            kelasViewModel.setType(null)

        }
        binding.cpInProgress.setOnClickListener {
            binding.cpAll.isChecked = false
            binding.cpSelesai.isChecked = false
            kelasViewModel.setType("ongoing")
        }

        binding.cpSelesai.setOnClickListener {
            binding.cpInProgress.isChecked = false
            binding.cpAll.isChecked = false
            kelasViewModel.setType("completed")
        }
    }
    private fun loadDataCategory() {
        lifecycleScope.launch {
            kelasViewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("data category", "list category view from database")
                    categoryCourseAdapter.setData(database.first().categoryResponse.data)
                    showRvCategory()
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
                        showRvCategory()
                        categoryCourseAdapter.setData(it.data)
                    }
                }

                Status.ERROR -> {
                    Log.e("Cek Data Category", it.message.toString())
                    binding.shimmerCategory.apply {
                        stopShimmer()
                        visibility = View.INVISIBLE
                    }
                    loadDataCategory()
                }

                Status.LOADING -> {
                    binding.shimmerCategory.startShimmer()
                }
            }

        }
    }

    private fun getKelasUser(type:String?){
        kelasViewModel.getListKelas(type)
        kelasViewModel.listKelas.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Category", Gson().toJson(it.data))
                    it.data?.let {
                        showRvCourse()
                        kelasAdapter.setData(it.data)

                    }
                }

                Status.ERROR -> {
                    binding.shimmerFrameLayout.apply {
                        stopShimmer()
                        visibility = View.INVISIBLE
                    }
                }

                Status.LOADING -> {
                    binding.shimmerFrameLayout.startShimmer()
                }
            }
        }
    }

    private fun showRvCourse() {
        binding.shimmerFrameLayout.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
        binding.rvKelas.visibility = View.VISIBLE
    }

    private fun showRvCategory() {
        binding.shimmerCategory.apply {
            stopShimmer()
            visibility = View.INVISIBLE
        }
        binding.rvCategoryCourse.visibility = View.VISIBLE
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

        kelasAdapter = KelasAdapter(emptyList(), listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.detailCourseFragment, bundle)
        }, isFull = false)

        binding.rvKelas.adapter = kelasAdapter
        binding.rvKelas.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategoryCourse.adapter = categoryCourseAdapter

    }


}