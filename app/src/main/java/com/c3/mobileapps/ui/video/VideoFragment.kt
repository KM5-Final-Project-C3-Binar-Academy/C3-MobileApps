package com.c3.mobileapps.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.c3.mobileapps.data.remote.ApiClient
import com.c3.mobileapps.databinding.FragmentVideoBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import retrofit2.Retrofit
import java.io.IOException

class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container,false)
        webView = binding.wvVideo
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Panggil API menggunakan retrofit
        ApiClient.getVideoUrl("https://api-binar-backend.risalamin.com/docs/#/courses/courses-data/video", object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                //Handle failure
                
            }

            override fun onResponse(call: Call, response: Response) {
                if(
                    response.isSuccessful{
                        val videoUrl = response.body?.string()
                        webView.webViewClient = WebViewClient()
                        webView.loadUrl(videoUrl)
                    }else{

                }
                )
            }
        })
    }
}