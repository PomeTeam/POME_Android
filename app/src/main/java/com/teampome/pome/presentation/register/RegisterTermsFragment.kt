package com.teampome.pome.presentation.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRegisterTermsBinding
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.register.RegisterTermsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterTermsFragment : BaseFragment<FragmentRegisterTermsBinding>(R.layout.fragment_register_terms) {
    private val viewModel by viewModels<RegisterTermsViewModel>()

    private val checkColor by lazy {
        resources.getColor(R.color.main, null)
    }

    private val notCheckColor by lazy {
        resources.getColor(R.color.grey_3, null)
    }

    override fun initView() {
        Log.d("test", "terms fragment in")

        CommonUtil.disabledPomeBtn(binding.registerAgreeAcb)
    }

    override fun initListener() {
        // 전체 동의 클릭
        binding.registerTermsAllAgreeAtv.setOnClickListener {
            if(viewModel.agreeAllCheck.value == true) {
                viewModel._agreeAllCheck.value = false
                viewModel._agreeUsingTermsCheck.value = false
                viewModel._agreePrivacyCheck.value = false
                viewModel._agreeMarketingCheck.value = false
            } else {
                viewModel._agreeAllCheck.value = true
                viewModel._agreeUsingTermsCheck.value = true
                viewModel._agreePrivacyCheck.value = true
                viewModel._agreeMarketingCheck.value = true
            }
        }

        // 이용약관 동의 체크 클릭
        binding.registerAgreeUsingTermsCheckAiv.setOnClickListener {
            viewModel._agreeUsingTermsCheck.value = viewModel.agreeUsingTermsCheck.value != true
        }

        // 이용약관 동의 텍스트 클릭
        binding.registerAgreeUsingTermsAtv.setOnClickListener {
            CommonUtil.goToWebPage(requireContext(), "https://few-horse-2aa.notion.site/6a2e6f6241e94a479a3e2c2a3cdb909e")
        }

        // 개인정보 수집 동의 체크 클릭
        binding.registerAgreePrivacyCheckAiv.setOnClickListener {
            viewModel._agreePrivacyCheck.value = viewModel.agreePrivacyCheck.value != true
        }

        // 개인정보 수집 동의 텍스트 클릭
        binding.registerAgreePrivacyAtv.setOnClickListener {
            CommonUtil.goToWebPage(requireContext(), "https://few-horse-2aa.notion.site/b396b02d8bd3460f945cf3f90935667b")
        }

        // 마케팅 정보 수집 동의 체크 클릭
        binding.registerAgreeMarketingCheckAiv.setOnClickListener {
            viewModel._agreeMarketingCheck.value = viewModel.agreeMarketingCheck.value != true
        }

        // 마케팅 정보 수집 동의 텍스트 클릭
        binding.registerAgreeMarketingAtv.setOnClickListener {
            CommonUtil.goToWebPage(requireContext(), "https://few-horse-2aa.notion.site/8cc486236d77473f84fa53a3b0ede726")
        }

        // 전체동의 체크시 뷰
        viewModel.agreeAllCheck.observe(viewLifecycleOwner) {
            if(it) {
                binding.registerAllAgreeCheckAiv.setColorFilter(checkColor)
            } else {
                binding.registerAllAgreeCheckAiv.setColorFilter(notCheckColor)
            }

            checkAgreeButtonActive()
        }

        // 이용약관 동의 체크 시 뷰
        viewModel.agreeUsingTermsCheck.observe(viewLifecycleOwner) {
            if(it) {
                binding.registerAgreeUsingTermsCheckAiv.setColorFilter(checkColor)
            } else {
                binding.registerAgreeUsingTermsCheckAiv.setColorFilter(notCheckColor)
            }

            checkAllAgreeChecking()
            checkAgreeButtonActive()
        }

        // 개인정보 동의 체크 시 뷰
        viewModel.agreePrivacyCheck.observe(viewLifecycleOwner) {
            if(it) {
                binding.registerAgreePrivacyCheckAiv.setColorFilter(checkColor)
            } else {
                binding.registerAgreePrivacyCheckAiv.setColorFilter(notCheckColor)
            }

            checkAllAgreeChecking()
            checkAgreeButtonActive()
        }

        // 마케팅 동의 체크 시 뷰
        viewModel.agreeMarketingCheck.observe(viewLifecycleOwner) {
            if(it) {
                binding.registerAgreeMarketingCheckAiv.setColorFilter(checkColor)
            } else {
                binding.registerAgreeMarketingCheckAiv.setColorFilter(notCheckColor)
            }

            checkAllAgreeChecking()
            checkAgreeButtonActive()
        }

        binding.registerAgreeAcb.setOnClickListener {
            moveToRegisterProfile()
        }
    }

    /**
     *  동의했어요 버튼 활성화 여부 판단 (한 메소드에 한가지 역할!)
     */
    private fun isAgreeButtonActive() : Boolean {
        // 필수가 다 켜진 경우에만 버튼 활성화
        return viewModel.agreeUsingTermsCheck.value == true && viewModel.agreePrivacyCheck.value == true
    }

    /**
     *  동의했어요 버튼 활성화 여부에 따른 뷰 변경
     */
    private fun checkAgreeButtonActive() {
        if(isAgreeButtonActive()) {
            CommonUtil.enabledPomeBtn(binding.registerAgreeAcb)
        } else {
            CommonUtil.disabledPomeBtn(binding.registerAgreeAcb)
        }
    }

    /**
     *  부분 동의 확인하여 전체 동의 체크 여부
     */
    private fun isAllAgreeChecking() : Boolean {
        return viewModel.agreeUsingTermsCheck.value == true
            && viewModel.agreePrivacyCheck.value == true
                && viewModel.agreeMarketingCheck.value == true
    }

    /**
     *  부분 동의 확인 후 전체 동의 뷰 변경
     */
    private fun checkAllAgreeChecking() {
        viewModel._agreeAllCheck.value = isAllAgreeChecking()
    }

    /**
     *  profile register로 이동
     */
    private fun moveToRegisterProfile() {
        val registerTermsToRegisterProfileAction = RegisterTermsFragmentDirections.actionRegisterTermsFragmentToRegisterProfileFragment()
        findNavController().navigate(registerTermsToRegisterProfileAction)
    }

    /**
     *  이용약관 디테일 뷰로 이동
     */
    private fun moveToTermsDetail() {
        val registerTermsToTermsDetailAction = RegisterTermsFragmentDirections.actionRegisterTermsFragmentToTermsDetailFragment(
            termsDetailTitle = "개인 정보보호 동의",
            termsDetailContent = "포미은 ~수집을 위해 아래와 같이 개인정보를 수집, 이용하고자 합니다. 내용을 읽으신 후 동의 여부를 결정하여 주십시오.\n\n" +
                    "개인정보 수집 이용 내역\n" +
                    "수집, 이용 항목 : 이름, 휴대전화 번호\n" +
                    "수집, 이용 목적 : ~ 수집\n" +
                    "개인정보 보관기간 : 수집일로부터 1년\n\n" +
                    "수집된 개인정보는 ~에 한해 이용되며, 목적 달성 후 안전하게 파기 됩니다. 개인 정보 수집 및 이용에 대해 거부하실 수 있으며, 동의를 거부하실 경우 ~ 불가합니다."
        )
        findNavController().navigate(registerTermsToTermsDetailAction)
    }
}