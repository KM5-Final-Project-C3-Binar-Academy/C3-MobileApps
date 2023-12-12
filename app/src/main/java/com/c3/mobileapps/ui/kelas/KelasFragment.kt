package com.c3.mobileapps.ui.kelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.FragmentKelasBinding
import com.c3.mobileapps.databinding.FragmentSearchBinding


class KelasFragment : Fragment() {

    private lateinit var binding: FragmentKelasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }


}