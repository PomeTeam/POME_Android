package com.teampome.pome.presentation.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentLoginSplashBinding
import com.teampome.pome.util.base.BaseFragment

class SplashLoginFragment : BaseFragment<FragmentLoginSplashBinding>(R.layout.fragment_login_splash) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 두번 클릭후 접속할 때, 크래시 발생하여 popBackStack 호출
        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("backPress", "backPressedCall")
                findNavController().popBackStack()
            }
        })
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.loginStartBtnAcb.setOnClickListener {
            moveToRegister()
        }
    }

    private fun moveToRegister() {
        val loginToRegisterAction = SplashLoginFragmentDirections.actionSplashLoginFragmentToRegisterFragment()
        findNavController().navigate(loginToRegisterAction)
    }
}