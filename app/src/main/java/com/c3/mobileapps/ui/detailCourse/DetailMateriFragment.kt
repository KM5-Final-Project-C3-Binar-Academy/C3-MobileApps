package com.c3.mobileapps.ui.detailCourse


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.c3.mobileapps.adapters.CourseMaterialAdapter
import com.c3.mobileapps.data.remote.model.response.courseMe.MateriKursus
import com.c3.mobileapps.databinding.FragmentDetailMateriBinding
import com.c3.mobileapps.ui.webView.WebView
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

        detailCourseViewModel.isLogin.observe(viewLifecycleOwner){
            if (it){
                getCourseMaterial(simpleArgs)
                Log.e("cek get api", "ini login")

            }else{
                getCourseDetail(simpleArgs)
                Log.e("cek get api", "ini gak login")
            }
        }
    }

    private fun getCourseMaterial(id: String?){
        detailCourseViewModel.getCourseByUser(id)
        detailCourseViewModel.listKelas.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Material", Gson().toJson(it.data))

                    it.data?.let {
                        val data = it.data

                        data.courseChapter?.forEach {chapter ->
                            if (chapter != null) {
                                materiList.add(chapter)
                                chapter.courseMaterial?.forEach {material ->
                                    chapter.orderIndex?.let { it1 ->
                                        MateriKursus(it1, material) }
                                        ?.let { it2 -> materiList.add(it2) }
                                }
                            }
                        }

                        courseMaterialAdapter = CourseMaterialAdapter(materiList,
                            listener = { data ->
                                Log.e("check listener", data.toString())
                                if (data == false) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Silahkan Checkout Terlebih dahulu!", Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    Log.e("check data material", data.toString())
                                    val bundle = bundleOf("courseMaterial" to data)
                                    val intent = Intent(activity, WebView::class.java)
                                    intent.putExtra("courseMaterial", bundle)
                                    startActivity(intent)
                                }
                            })

                        Log.e("Enrolled",data.totalCompletedMaterial.toString())

                        if (data.totalCompletedMaterial == null){
                            courseMaterialAdapter.setEnrolled(false)
                        }else{
                            courseMaterialAdapter.setEnrolled(true)
                        }


                        binding.rvMateri.adapter = courseMaterialAdapter
                        binding.rvMateri.layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )

                        Log.e("ListMateri",materiList.toString())

                    }
                }

                Status.ERROR -> {

                }

                Status.LOADING -> {

                }
            }
        }

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
                                chapter.orderIndex?.let { it1 ->
                                    Log.e("Materi",material?.video.toString())
                                    MateriKursus(it1, material) }
                                    ?.let { it2 -> materiList.add(it2) }
                            }
                        }
                    }

                    courseMaterialAdapter = CourseMaterialAdapter(materiList,
                        listener = { data ->
                            Log.e("check listener", data.toString())
                            if (data == false) {

                                Toast.makeText(
                                    requireActivity(),
                                    "Anda Belum Login!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val bundle = bundleOf("courseMaterial" to data)
                                val intent = Intent(activity, WebView::class.java)
                                intent.putExtra("courseMaterial", bundle)
                                startActivity(intent)
                            }
                        })

                    Log.e("Enrolled",data?.totalCompletedMaterial.toString())

                    if (data?.totalCompletedMaterial == null){
                        courseMaterialAdapter.setEnrolled(false)
                    }else{
                        courseMaterialAdapter.setEnrolled(true)
                    }

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
