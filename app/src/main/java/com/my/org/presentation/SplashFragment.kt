package com.my.org.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.my.org.R

class SplashFragment : Fragment() {
    private lateinit var slideAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        slideAnimation = AnimationUtils.loadAnimation(context, R.anim.splash_slide_anim)
        val splashName = view.findViewById<TextView>(R.id.tv_splash_label)
        splashName.animation = slideAnimation
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }, 2000L)
        // Inflate the layout for this fragment
        return view
    }


}