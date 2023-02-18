package com.teampome.pome.presentation.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentSplashBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.safeNavigate
import com.teampome.pome.util.token.TokenManager
import com.teampome.pome.util.token.UserManager
import com.teampome.pome.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// Todo : Android 12부터 Splash가 default 되어있기 때문에 따로 커스텀이 필요

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash){

    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var tokenManager: TokenManager

    private val viewModel: SplashViewModel by viewModels()

    /*
     *  네트워크 확인
     *  회원 확인
     *  (UserManager의 phonNum정보가 없는가? 혹은 로그인 시 정상적으로 토큰이 떨어지는가?)
     *  회원이면 -> recordView
     *  비회원이면 -> RegisterView
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phoneNum = runBlocking {
            userManager.getUserPhone().first()
        }

        // 네트워크 체크 + phoneNum 정보확인
        if(CommonUtil.checkNetwork(requireContext()) &&
                !phoneNum.isNullOrEmpty()) {

            viewModel.login(phoneNum, object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    // 타임아웃 및 ApiRequset 도중 에러가 발생한 경우,
                    Log.e("login", "error by $message")
                    moveToLogin()
                }
            })
        } else {
            moveToLogin()
        }
    }

    override fun initView() {
    }

    override fun initListener() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("token", "userInfo : $it")

                    it.data.data?.let { userInfo ->
                        // signIn 정보를 토대로 토큰 저장
                        runBlocking {
                            if(tokenManager.getToken().first() != null) {
                                tokenManager.deleteToken()
                            }

                            tokenManager.saveToken(userInfo.accessToken)

                            if(userManager.getUserId().first() != null) {
                                userManager.deleteUserId()
                            }
                            userManager.saveUserId(userInfo.userId)

                            if(userManager.getUserNickName().first() != null) {
                                userManager.deleteUserNickName()
                            }
                            userManager.saveUserNickName(userInfo.nickname)
                        }

                        moveToRecord()
                    } ?: run {
                        if(it.data.success) {
                            Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                        } else {
                            // 로그인이 실패한 경우이다.
                            moveToLogin()
                        }
                    }
                }
                is ApiResponse.Failure -> {
                    // 서버에서 에러가 발생한 경우
                    moveToLogin()
                }
                is ApiResponse.Loading -> {

                }
            }
        }
    }

    private fun dummyTime(second: Long) {
        runBlocking {
            delay(second * 1000)
        }
    }

    private fun moveToLogin() {
        dummyTime(1)

        val splashToLoginAction = SplashFragmentDirections.actionSplashFragmentToSplashLoginFragment()
        findNavController().safeNavigate(splashToLoginAction)
    }

    private fun moveToRecord() {
        dummyTime(1)

        val splashToRecordAction = SplashFragmentDirections.actionSplashFragmentToRecordFragment()
        findNavController().navigate(splashToRecordAction)
    }
}