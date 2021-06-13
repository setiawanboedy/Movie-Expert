package com.attafakkur.movieexpert.splash

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.attafakkur.movieexpert.MainActivity
import com.attafakkur.movieexpert.R
import com.attafakkur.movieexpert.databinding.FragmentSplashBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {

    companion object {
        private const val SPLASH_TIME_OUT = 1500L
    }

    private val binding by viewBinding(FragmentSplashBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvSplash.visibility = View.GONE
        binding.lotteAnim.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                binding.tvSplash.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

                }, SPLASH_TIME_OUT)
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}

        })

    }

    override fun onResume() {
        super.onResume()
        with((activity as MainActivity)) {
            supportActionBar?.hide()
            findViewById<BottomNavigationView>(R.id.bottom_nav)
                ?.visibility = View.GONE
        }

    }

}