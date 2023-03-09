package com.teampome.pome.presentation.remind

import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRemindItemDetailBinding
import com.teampome.pome.util.base.BaseFragment

class RemindDetailFragment :
    BaseFragment<FragmentRemindItemDetailBinding>(R.layout.fragment_remind_item_detail) {

//    private val args: RemindDetailFragmentArgs by navArgs()

    override fun initView() {
//        binding.contentCardItem = args.remindContentsItem
//        binding.firstEmotion = CommonUtil.getEmotionData(args.remindContentsItem.firstThink)
//        binding.lastEmotion = CommonUtil.getEmotionData(args.remindContentsItem.lastThink)
        binding.executePendingBindings()
    }

    override fun initListener() {
        // 뒤로가기 버튼
        binding.remindContentsDetailLeftArrowAiv.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}