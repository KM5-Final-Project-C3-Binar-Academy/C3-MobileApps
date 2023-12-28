package com.c3.mobileapps.ui.detailCourse

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.adapters.PagerAdapter
import com.c3.mobileapps.data.remote.model.response.course.Course
import com.c3.mobileapps.databinding.FragmentDetailCourseBinding
import com.c3.mobileapps.ui.payment.OnBoardingBottomSheet
import com.c3.mobileapps.ui.nonlogin.NonLoginBottomSheet
import com.c3.mobileapps.ui.payment.BottomSheetPayment
import com.c3.mobileapps.ui.payment.PaymentViewModel
import com.c3.mobileapps.utils.Status
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.android.ext.android.inject


class DetailCourseFragment : Fragment() {

    private var _binding: FragmentDetailCourseBinding? = null
    private val binding get() = _binding!!
    private val detailCourseViewModel: DetailCourseViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataDetail = arguments?.getParcelable<Course?>("pickItem")
        dataDetail?.let {
            //get id to retrieve courseId API
            val idCourse = dataDetail.id
            getCourseDetail(idCourse)

            //get id youtube
            val urlIntro = dataDetail.introVideo
            val convertId = convertId(urlIntro)
            playIntro(convertId)
        }


        //* Setup ViewPager n passing data to viewpager *//
        val idcourse = dataDetail?.id
        val fragment = arrayListOf(
            DetailTentangFragment.newInstance(dataDetail),
            DetailMateriFragment.newInstance(idcourse, clicked = {
                detailCourseViewModel.isLogin.observe(viewLifecycleOwner) {
                    if (it) {
                        val bottomSheetPayment = BottomSheetPayment(dataDetail!!,R.id.detailCourseFragment)
                        bottomSheetPayment.show(childFragmentManager, bottomSheetPayment.tag)
                    } else {
                        val nonLoginBottomSheet = NonLoginBottomSheet(R.id.detailCourseFragment)
                        nonLoginBottomSheet.show(childFragmentManager, nonLoginBottomSheet.tag)

                    }
                }
            })
        )
        val titleFragment = arrayListOf("Tentang", "Materi Kelas")
        val viewPager2AdapterAdapter = PagerAdapter(requireActivity(), fragment)
        binding.viewPager.adapter = viewPager2AdapterAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()





        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    //retrieve courseId API
    @SuppressLint("SetTextI18n")
    private fun getCourseDetail(id: String?) {
        detailCourseViewModel.getCourseByUser(id)
        detailCourseViewModel.listKelas.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Course", Gson().toJson(it.data))
                    binding.progressBar.isVisible = false

                    val data = it.data?.data
                    binding.tvNamaKelas.text = data?.courseCategory?.name
                    binding.deskripsiJudulKelas.text = data?.name
                    binding.creatorKelas.text = data?.author
                    binding.levelNameKelas.text = data?.difficulty
                    binding.rating.text = data?.rating.toString()
                    binding.durasiKelas.text = "${data?.totalDuration.toString()} Menit"
                    binding.jumlahModulKelas.text = "${data?.totalMaterials.toString()} Modul "

                }

                Status.ERROR -> {
                    Log.e("Cek Data Course", it.message.toString())
                    binding.progressBar.isVisible = false
                }

                Status.LOADING -> {
                    binding.progressBar.isVisible = true

                }
            }
        }
    }


    private fun playIntro(id: String?) {

        val youTubePlayerView = binding.webView
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(id.toString(), 0f)
            }
        })

        viewLifecycleOwner.lifecycle.addObserver(youTubePlayerView)
    }

    private fun convertId(text: String?): String {
        val parts = text?.split("/")

        if (text!!.contains("https://youtu.be/")) {
            return parts!![parts.size - 1]
        }

        if (text.contains("https://www.youtube.com/") && text.contains("watch?v=")) {
            if (parts != null) {
                return (parts[parts.size - 1]).replace("watch?v=", "")
            }
        }
        return ""
    }


}