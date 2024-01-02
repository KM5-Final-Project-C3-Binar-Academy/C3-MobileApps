package com.c3.mobileapps.ui.kelas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.CategoryAdapter
import com.c3.mobileapps.adapters.KelasAdapter
import com.c3.mobileapps.databinding.FragmentViewAllKelasBinding
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class ViewAllKelasFragment : Fragment() {

    private lateinit var binding: FragmentViewAllKelasBinding

    private val kelasViewModel: KelasViewModel by inject()
    private lateinit var kelasAdapter: KelasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewAllKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getKelasUser()
        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getKelasUser() {
        kelasViewModel.getListKelas(null)
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
        binding.rvViewAllKelas.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun setupRecyclerView() {
        kelasAdapter = KelasAdapter(emptyList(), listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.detailCourseFragment, bundle)
        }, isFull = true)

        binding.title.text = "List Kursus Saya"
        binding.rvViewAllKelas.adapter = kelasAdapter
        binding.rvViewAllKelas.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

    }


}