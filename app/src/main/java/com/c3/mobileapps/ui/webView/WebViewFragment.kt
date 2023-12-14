package com.c3.mobileapps.ui.webView

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.c3.mobileapps.R
import com.c3.mobileapps.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding

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
        binding.webView.loadUrl(urlIntro.toString())
        binding.webView.settings.javaScriptEnabled = true

        val webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, urlIntro)
            }
        }
        binding.webView.webViewClient = webViewClient

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}