package com.c3.mobileapps.ui.detailCourse

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.databinding.FragmentDetailTentangBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class DetailTentangFragment : Fragment() {
    private lateinit var binding: FragmentDetailTentangBinding
    private val detailCourseViewModel:DetailCourseViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailTentangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val simpleArgs = arguments?.getString("ARGS_ID")
        getCourseDetail(simpleArgs)

    }

    @SuppressLint("SetTextI18n")
    private fun getCourseDetail(id: String?){
        detailCourseViewModel.getCourseById(id)
        detailCourseViewModel.courseById.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data Course", Gson().toJson(it.data))

                    binding.tvTentangkelas.text = it.data?.data?.description
                    binding.tvAudience.text = it.data?.data?.target_audience.toString()
                    val telegram = it.data?.data?.telegram
                    binding.btnTelegram.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegram))
                        startActivity(intent)
                    }

                }
                Status.ERROR -> {
                    Log.e("Cek Data Course", it.message.toString())
                }

                Status.LOADING -> {

                }
            }
        }
    }

    companion object {
        fun newInstance(simpleArgs: String?): Fragment {
            val args = Bundle()
            args.putString("ARGS_ID", simpleArgs)
            val fragment = DetailTentangFragment()
            fragment.arguments = args
            return fragment
        }
    }


}