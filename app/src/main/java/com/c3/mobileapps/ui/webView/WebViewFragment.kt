package com.c3.mobileapps.ui.webView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.data.remote.model.response.course.CourseMaterial
import com.c3.mobileapps.databinding.FragmentWebViewBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import org.koin.android.ext.android.inject


@Suppress("DEPRECATION")
class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    private lateinit var youTubePlayer: YouTubePlayer
    private val webViewViewModel: WebViewViewModel by inject()

    var isFullscreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullscreen) {
                // if the player is in fullscreen, exit fullscreen
                youTubePlayer.toggleFullscreen()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
       binding = FragmentWebViewBinding.inflate(inflater, container, false)
       return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getParcelable<CourseMaterial>("courseMaterial")
        val idConvert = convertId(data?.video.toString())
        data?.courseMaterialStatus?.forEach{
            val idMaterial = it?.id
            playVideo(idConvert, idMaterial)
        }


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun playVideo(id: String?, materialId: String?){

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val youTubePlayerView = binding.youtubePlayerView
        val fullscreenViewContainer = binding.fullScreenViewContainer

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1) // enable full screen button
            .build()

        youTubePlayerView.enableAutomaticInitialization = false

        youTubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullscreen = true

                // the video will continue playing in fullscreenView
                youTubePlayerView.visibility = View.GONE
                fullscreenViewContainer.visibility = View.VISIBLE
                fullscreenViewContainer.addView(fullscreenView)
            }

            override fun onExitFullscreen() {
                isFullscreen = false

                // the video will continue playing in the player
                youTubePlayerView.visibility = View.VISIBLE
                fullscreenViewContainer.visibility = View.GONE
                fullscreenViewContainer.removeAllViews()
            }
        })

        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@WebViewFragment.youTubePlayer = youTubePlayer
                youTubePlayer.loadVideo(id.toString(), 0f)
                putCourseMaterial(materialId)

                val enterFullscreenButton = binding.enterFullscreenButton
                enterFullscreenButton.setOnClickListener {
                    youTubePlayer.toggleFullscreen()
                }
            }
        }, iFramePlayerOptions)

        lifecycle.addObserver(youTubePlayerView)

    }

    private fun putCourseMaterial(id: String?) {
        webViewViewModel.getUpdateMaterial(id)
        webViewViewModel.materialResp.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("Cek Data Material", Gson().toJson(it.data))
                }

                Status.ERROR -> {
                    Log.e("Cek Data Material", it.message.toString())
                }
                Status.LOADING -> {
                }
            }
        }
    }

    private fun convertId(text: String?): String {
        val parts = text?.split("/")

        if(text!!.contains("https://youtu.be/")){
            return parts!![parts.size -1]
        }

        if(text.contains("https://www.youtube.com/") && text.contains("watch?v=")){
            if (parts != null) {
                return (parts[parts.size -1]).replace("watch?v=", "")
            }
        }

        return ""
    }
}