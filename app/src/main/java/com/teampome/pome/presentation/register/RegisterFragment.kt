package com.teampome.pome.presentation.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRegisterBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.databinding.PomeRegisterBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil.getPixelToDp
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.util.token.TokenManager
import com.teampome.pome.util.token.UserManager
import com.teampome.pome.viewmodel.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var pomeBottomSheetDialog: BottomSheetDialog
    private lateinit var pomeBottomSheetDialogBinding: PomeRegisterBottomSheetDialogBinding

    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var tokenManager: TokenManager

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

    override fun initView() {
        // pomeBottomSheetDialog 뷰 인플레이션 과정
        pomeBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeBottomSheetDialogBinding = PomeRegisterBottomSheetDialogBinding.inflate(layoutInflater, null, false)
        pomeBottomSheetDialog.setContentView(pomeBottomSheetDialogBinding.root)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        viewModel.smsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("test", "data : ${it.data}")

                    it.data.data?.let { smsData ->
                        viewModel.smsValidate = smsData.value
                    }
                }

                is ApiResponse.Failure -> {
                    Log.d("test", "fail : ${it.errorMessage}")
                }

                is ApiResponse.Loading -> {
                }
            }
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.let { userInfo ->
                        // signIn 정보를 토대로 토큰 저장
                        runBlocking {
                            if (tokenManager.getToken().first() != null) {
                                tokenManager.deleteToken()
                            }

                            tokenManager.saveToken(userInfo.accessToken)

                            if (userManager.getUserId().first() != null) {
                                userManager.deleteUserId()
                            }
                            userManager.saveUserId(userInfo.userId)

                            if (userManager.getUserNickName().first() != null) {
                                userManager.deleteUserNickName()
                            }
                            userManager.saveUserNickName(userInfo.nickname)
                        }

                        moveToRecord()
                    } ?: run {
                        Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    }

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    // 서버에서 에러가 발생한 경우
                    if(it.code == "U0003") { // 가입된 번호가 없는 경우
                        moveToRegisterTerms()
                    } else {
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    hideLoading()
                }
                is ApiResponse.Loading -> {
                    showLoading()
                }
            }
        }

        // 키보드 자연스럽게 처리
        binding.registerCl.setOnTouchListener { _, _ ->
            CommonUtil.hideKeyboard(requireActivity())
            false
        }

        // 번호 입력
        binding.registerPhoneAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.registerPhone.value = it.toString()
                }

                settingAgreeButton()
            }
        })

        // 인증 번호
        binding.registerCertNumAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.registerCertNum.value = it.toString()
                }

                settingAgreeButton()
            }
        })

        // 번호 인증 요청
        binding.registerCertPhoneAcb.setOnClickListener {
            Toast.makeText(requireContext(), "번호 인증 요청", Toast.LENGTH_SHORT).show()

            binding.registerCertPhoneAcb.text = "재요청"

            binding.registerCertPhoneAcb.setPadding(
                getPixelToDp(requireContext(), 12),
                getPixelToDp(requireContext(), 4),
                getPixelToDp(requireContext(), 12),
                getPixelToDp(requireContext(), 4))

            viewModel.sendSms(object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Log.d("test", "error : $message")
                    Toast.makeText(requireContext(), "error : $message", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 동의하고 시작하기 버튼
        binding.registerAgreeAcb.setOnClickListener {
            if(viewModel.smsValidate == viewModel.registerCertNum.value) {

                // 인증이 완료된 유저 폰번호 저장
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.Main) {
                        userManager.saveUserPhone(viewModel.registerPhone.value ?: "")
                    }
                }

                checkNumber()
            } else {
                Toast.makeText(requireContext(), "인증번호가 다릅니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 뒤로가기 버튼
        binding.registerBackAiv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     *  galleryLauncher를 이용하여 사용자 이미지를 가져오는 작업
     */
    private fun settingAgreeButton() {
        if(checkAgreeBtnEnable()) {
            enableAgreeBtn()
        } else {
            disableAgreeBtn()
        }
    }

    /**
     *  button 활성화 여부 체크
     */
    private fun checkAgreeBtnEnable() : Boolean {
        return (!viewModel.registerPhone.value.isNullOrEmpty()
                && !viewModel.registerCertNum.value.isNullOrEmpty())
    }

    /**
     *  button 활성화 작업
     */
    private fun enableAgreeBtn() {
        binding.registerAgreeAcb.setBackgroundResource(R.drawable.register_profile_name_check_available_btn_background)
        binding.registerAgreeAcb.isClickable = true
    }

    /**
     *  button 비활성화 작업
     */
    private fun disableAgreeBtn() {
        binding.registerAgreeAcb.setBackgroundResource(R.drawable.register_profile_name_check_disable_btn_background)
        binding.registerAgreeAcb.isClickable = false
    }

    /**
     *  번호가 가입되어있는지 확인
     *  가입된 번호 : 토큰 저장 -> 기록 뷰
     *  비가입 번호 : 회원가입
     */
    private fun checkNumber() {
        viewModel.registerPhone.value?.let { number ->
            viewModel.login(number, object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Log.e("error", "가입 에러 발생")
                    Toast.makeText(requireContext(), "에러가 발생하여 번호인증을 재시도 부탁드립니다.", Toast.LENGTH_SHORT).show()
                }
            })
        } ?: run {
            Toast.makeText(requireContext(), "에러가 발생하여 번호인증을 재시도 부탁드립니다.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     *  register Terms로 이동
     */
    private fun moveToRegisterTerms() {
        val registerToRegisterTermsAction = RegisterFragmentDirections.actionRegisterFragmentToRegisterTermsFragment()
        findNavController().navigate(registerToRegisterTermsAction)
    }

    /**
     *  이미 가입된 번호라면 바로 기록뷰로 이동
     */
    private fun moveToRecord() {
        val registerToRecordAction = RegisterFragmentDirections.actionRegisterFragmentToRecordFragment()
        findNavController().navigate(registerToRecordAction)
    }
}