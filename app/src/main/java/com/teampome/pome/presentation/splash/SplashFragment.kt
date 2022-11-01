package com.teampome.pome.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.base.BaseFragment
import com.teampome.pome.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

// Todo : Android 12부터 Splash가 default 되어있기 때문에 따로 커스텀이 필요

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2초 뒤 register로 move
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(3000)
            moveToRegister()
        }
    }

    override fun initListener() {

    }

    private fun moveToRegister() {
        val splashToRegisterAction = SplashFragmentDirections.actionSplashFragmentToRegisterFragment()
        findNavController().navigate(splashToRegisterAction)
    }

    private fun moveToRecord() {
        val splashToRecordAction = SplashFragmentDirections.actionSplashFragmentToRecordFragment()
        findNavController().navigate(splashToRecordAction)
    }
}