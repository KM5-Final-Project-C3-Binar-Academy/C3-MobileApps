package com.c3.mobileapps.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.c3.mobileapps.R
import com.c3.mobileapps.ui.splashScreen.SplashScreenData


class SplashScreenAdapter(
    private var context: Context,
    private var SplashScreenDataList : List<SplashScreenData>
) : PagerAdapter() {
    override fun getCount(): Int {
        return SplashScreenDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view:View = LayoutInflater.from(context).inflate(R.layout.layout_splash_screen, null);
        val title : TextView
        var imageView : ImageView
        val desc : TextView

        imageView = view.findViewById(R.id.appLogoSplash)
        title = view.findViewById(R.id.titleSplash)
        desc = view.findViewById(R.id.descSplash)
        imageView = view.findViewById(R.id.imgIlust)


        imageView.setImageResource(SplashScreenDataList[position].imageUrl)
        title.text = SplashScreenDataList[position].title
        desc.text = SplashScreenDataList[position].desc

        container.addView(view)
        return view
    }
}