package com.c3.mobileapps.ui.detailCourse


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c3.mobileapps.databinding.FragmentDetailMateriBinding
import org.koin.android.ext.android.inject


class DetailMateriFragment : Fragment() {

    private lateinit var binding: FragmentDetailMateriBinding
    private val detailCourseViewModel:DetailCourseViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentDetailMateriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val simpleArgs = arguments?.getString("ARGS_ID")
        binding.textView6.text = simpleArgs


    }


    companion object {
        fun newInstance(simpleArgs: String?): Fragment {
            val args = Bundle()
            args.putString("ARGS_ID", simpleArgs)
            val fragment = DetailMateriFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
