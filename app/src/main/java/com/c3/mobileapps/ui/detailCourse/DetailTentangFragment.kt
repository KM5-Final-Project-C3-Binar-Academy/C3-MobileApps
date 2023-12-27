package com.c3.mobileapps.ui.detailCourse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentDetailTentangBinding
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
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

        val data = arguments?.getParcelable<Course>("ARGS_ID")



        binding.tvTentangkelas.text = data?.description
        binding.tvAudience.text = data?.targetAudience?.joinToString()
        val telegram = data?.telegram
        binding.btnTelegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegram))
            startActivity(intent)
        }


    }



    companion object {
        fun newInstance(simpleArgs: Course?): Fragment {
            val args = Bundle()
            args.putParcelable("ARGS_ID", simpleArgs)
            val fragment = DetailTentangFragment()
            fragment.arguments = args
            return fragment
        }
    }


}