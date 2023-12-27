package com.c3.mobileapps.ui.webView

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.data.remote.model.response.course.CourseMaterial
import com.c3.mobileapps.data.remote.model.response.updateCourseMaterial.DataMaterialStatus
import com.c3.mobileapps.databinding.ActivityWebViewBinding
import com.c3.mobileapps.utils.Status
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import org.koin.android.ext.android.inject


@Suppress("DEPRECATION")
class WebView : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val webViewViewModel: WebViewViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)

        //get data passing from detail materi
        val bundle = intent.extras?.getBundle("courseMaterial")
        val data = bundle?.getParcelable<CourseMaterial>("courseMaterial")
        Log.e("Cek Data yutub", bundle.toString())
        val idConvert = convertId(data?.video.toString())
        data?.courseMaterialStatus?.forEach{
            val idMaterial = it?.id
            playVideo(idConvert, idMaterial)
        }

        buttonBack()
    }
    private fun playVideo(id: String?, idMaterial: String?){

            val youTubePlayerView = binding.youtubePlayerView
            youTubePlayerView.enableAutomaticInitialization = false

            youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(id.toString(), 0f)

                        putCourseMaterial(idMaterial)

                }
//                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
//                    super.onVideoDuration(youTubePlayer, duration)
//                    val tracker = YouTubePlayerTracker()
//                    youTubePlayer.addListener(tracker)
//
//                    tracker.videoDuration
//                    tracker.currentSecond
//
//                    Log.e("Youtube duration ", tracker.videoDuration.toString())
//                    Log.e("Youtube second ", tracker.currentSecond.toString())
//
////                    if (tracker.videoDuration == tracker.currentSecond){
////
////                    }
//                }
            })

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
    private fun buttonBack(){
        binding.back.setOnClickListener {
            val navController = findNavController(R.id.nav_graph)
            navController.navigateUp()
            navController.navigate(R.id.detailCourseFragment)
        }
    }
}