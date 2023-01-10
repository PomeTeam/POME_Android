package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentConsumeEmotionBinding
import com.teampome.pome.util.base.BaseFragment

class ConsumeEmotionFragment : BaseFragment<FragmentConsumeEmotionBinding>(R.layout.fragment_consume_emotion) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        binding.consumeEmotionBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}