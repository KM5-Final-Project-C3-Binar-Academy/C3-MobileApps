package com.c3.mobileapps.ui.webView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.databinding.FragmentWebViewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions



class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    private lateinit var youTubePlayer: YouTubePlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
       binding = FragmentWebViewBinding.inflate(inflater, container, false)
       return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val urlIntro = arguments?.getString("Url")
        val idConvert = convertId(urlIntro)
        playVideo(idConvert)


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun playVideo(id: String?){

        var isFullscreen = false
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFullscreen) {
                    // if the player is in fullscreen, exit fullscreen
                    youTubePlayer.toggleFullscreen()
                }
            }
        }

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

                val enterFullscreenButton = binding.enterFullscreenButton
                enterFullscreenButton.setOnClickListener {
                    youTubePlayer.toggleFullscreen()
                }
            }
        }, iFramePlayerOptions)

        lifecycle.addObserver(youTubePlayerView)

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