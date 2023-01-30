package com.teampome.pome.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

// Todo : Android 12부터 Splash가 default 되어있기 때문에 따로 커스텀이 필요

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash){

    /*
     *  네트워크 확인
     *  회원 확인
     *  (UserManager의 phonNum정보가 없는가? 혹은 로그인 시 정상적으로 토큰이 떨어지는가?)
     *  회원이면 -> recordView
     *  비회원이면 -> RegisterView
     */
    private var isFirstLogin = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이미 로그인한 상태에 따라 나눠줘야할듯
        if(isFirstLogin) {
            // 2초 뒤 register로 move
            MainScope().launch(context = Dispatchers.Main) {
                delay(2000)
                moveToLogin()
            }
        } else {
           // 이미 로그인한 상태라면 main으로 이동
            MainScope().launch(context = Dispatchers.Main) {
                delay(2000)
                moveToRecord()
            }
        }
    }

    override fun initView() {

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