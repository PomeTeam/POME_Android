package com.teampome.pome.presentation.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRegisterBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
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

    @SuppressLint("ClickableViewAccessibility")
    override fun initListener() {
        // 키보드 자연스럽게 처리
        binding.registerCl.setOnTouchListener { _, _ ->
            CommonUtil.hideKeyboard(requireActivity())
            false
        }

        // Todo : two-way binding
        // 이름 입력
        binding.registerNameAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        // 번호 입력
        binding.registerPhoneAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        // 인증 번호
        binding.registerCertNumAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        // 번호 인증 요청
        binding.registerCertPhoneAcb.setOnClickListener {
            Toast.makeText(requireContext(), "번호 인증 요청", Toast.LENGTH_SHORT).show()
            binding.registerCertPhoneAcb.text = "재요청"
        }

        // 동의하고 시작하기 버튼
        binding.registerAgreeAcb.setOnClickListener {
            Toast.makeText(requireContext(), "동의하고 시작하기", Toast.LENGTH_SHORT).show()
        }

        // 뒤로가기 버튼
        binding.registerBackAiv.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}