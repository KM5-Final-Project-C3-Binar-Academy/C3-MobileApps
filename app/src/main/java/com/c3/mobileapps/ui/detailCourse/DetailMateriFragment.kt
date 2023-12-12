package com.c3.mobileapps.ui.detailCourse


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.CourseMaterialAdapter
import com.c3.mobileapps.databinding.FragmentDetailMateriBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import org.koin.android.ext.android.inject


class DetailMateriFragment : Fragment() {

    private lateinit var binding: FragmentDetailMateriBinding
    private val detailCourseViewModel:DetailCourseViewModel by inject()

    private var materiList: MutableList<Any> = mutableListOf()
    private lateinit var courseMaterialAdapter: CourseMaterialAdapter

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

        getCourseDetail(simpleArgs)

        courseMaterialAdapter = CourseMaterialAdapter(materiList)

        binding.rvMateri.adapter = courseMaterialAdapter
        binding.rvMateri.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

    }

    private fun getCourseDetail(id: String?){
        detailCourseViewModel.getCourseById(id)
        detailCourseViewModel.courseById.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    Log.e("Cek Data Course", Gson().toJson(it.data))

                    val data = it.data?.data

                    data?.courseChapter?.forEach {chapter ->
                        if (chapter != null) {
                            materiList.add(chapter)
                            chapter.courseMaterial?.forEach {material ->
                                materiList.add(material!!)
                            }
                        }

                    }

                    courseMaterialAdapter = CourseMaterialAdapter(materiList)

                    binding.rvMateri.adapter = courseMaterialAdapter
                    binding.rvMateri.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    Log.e("ListMateri",materiList.toString())

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
            val fragment = DetailMateriFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
