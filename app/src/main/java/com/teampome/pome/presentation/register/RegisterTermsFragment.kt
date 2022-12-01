package com.teampome.pome.presentation.register

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRegisterTermsBinding
import com.teampome.pome.util.base.BaseFragment

class RegisterTermsFragment : BaseFragment<FragmentRegisterTermsBinding>(R.layout.fragment_register_terms) {
    override fun initListener() {
        binding.registerAgreeAcb.setOnClickListener {
            moveToRegisterProfile()
        }
    }

    /**
     *  profile register로 이동
     */
    private fun moveToRegisterProfile() {
        val registerTermsToRegisterProfileAction = RegisterTermsFragmentDirections.actionRegisterTermsFragmentToRegisterProfileFragment()
        findNavController().navigate(registerTermsToRegisterProfileAction)
    }
}