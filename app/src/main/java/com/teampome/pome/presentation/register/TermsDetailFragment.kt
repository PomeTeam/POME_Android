package com.teampome.pome.presentation.register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentTermsDetailBinding
import com.teampome.pome.util.base.BaseFragment

class TermsDetailFragment : BaseFragment<FragmentTermsDetailBinding>(R.layout.fragment_terms_detail) {

    private val termsDetailArgs: TermsDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        binding.termsDetailTitleTv.text = termsDetailArgs.termsDetailTitle
        binding.termsDetailContentsTv.text = termsDetailArgs.termsDetailContent
    }

    override fun initListener() {
        binding.termsDetailBackAiv.setOnClickListener {
            moveToBack()
        }
    }

    private fun moveToBack() {
        findNavController().popBackStack()
    }
}