package com.teampome.pome.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

// Todo : Android 12부터 Splash가 default 되어있기 때문에 따로 커스텀이 필요

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash){

    private var isFirstLogin = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이미 로그인한 상태에 따라 나눠줘야할듯
        if(isFirstLogin) {
            // 2초 뒤 register로 move
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(3000)
                moveToLogin()
            }
        } else {
           // 이미 로그인한 상태라면 main으로 이동
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(3000)
                moveToRecord()
            }
        }
    }

    override fun initListener() {

    }

    private fun moveToLogin() {
        val splashToLoginAction = SplashFragmentDirections.actionSplashFragmentToSplashLoginFragment()
        findNavController().navigate(splashToLoginAction)
    }

    private fun moveToRecord() {
        val splashToRecordAction = SplashFragmentDirections.actionSplashFragmentToRecordFragment()
        findNavController().navigate(splashToRecordAction)
    }
}