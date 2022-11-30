package com.teampome.pome.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    }

    override fun initListener() {
        binding.loginStartBtnAcb.setOnClickListener {
            // kakao 인증 후 register 화면으로 이동
            moveToRegister()
        }
        
        // kakao login btn click
        binding.loginKakaoBtnAiv.setOnClickListener {
            Toast.makeText(requireContext(), "kakao login", Toast.LENGTH_SHORT).show()
        }
        
        // google login btn click
        binding.loginGoogleBtnAiv.setOnClickListener {
            Toast.makeText(requireContext(), "google login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToRegister() {
        val loginToRegisterAction = SplashLoginFragmentDirections.actionSplashLoginFragmentToRegisterFragment()
        findNavController().navigate(loginToRegisterAction)
    }
}