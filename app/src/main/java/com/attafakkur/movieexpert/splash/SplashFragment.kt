package com.attafakkur.movieexpert.splash

import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.attafakkur.movieexpert.MainActivity
import com.attafakkur.movieexpert.R
import com.attafakkur.movieexpert.databinding.FragmentSplashBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SplashFragment : Fragment() {

    companion object {
        private const val SPLASH_TIME_OUT = 1500L
    }

    private var _fragmentSplashBinding: FragmentSplashBinding? = null
    private val binding get() = _fragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.splashTextview?.visibility = View.GONE
        binding?.splashLottie?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                binding?.splashTextview?.visibility = View.VISIBLE

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
        with((activity as MainActivity)){
            supportActionBar?.hide()
            findViewById<BottomNavigationView>(R.id.bottom_nav)
                ?.visibility = View.GONE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.hide(WindowInsets.Type.statusBars())
            }
            else {
                @Suppress("DEPRECATION")
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }

    }

}