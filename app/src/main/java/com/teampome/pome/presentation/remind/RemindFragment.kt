package com.teampome.pome.presentation.remind

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRemindBinding
import com.teampome.pome.databinding.PomeRemindBottomSheetDialogBinding
import com.teampome.pome.util.Constants.FIRST_EMOTION
import com.teampome.pome.util.Constants.LAST_EMOTION

class RemindFragment : BaseFragment<FragmentRemindBinding>(R.layout.fragment_remind) {

    private lateinit var pomeRemindBottomSheetDialogBinding: PomeRemindBottomSheetDialogBinding
    private lateinit var pomeRemindBottomSheetDialog: BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pomeRemindBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeRemindBottomSheetDialogBinding = PomeRemindBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        pomeRemindBottomSheetDialog.setContentView(pomeRemindBottomSheetDialogBinding.root)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {
        // 처음 감정 선택
        binding.remindReviewFirstEmotionCl.setOnClickListener {
            pomeRemindBottomSheetDialogBinding.apply {
                remindDialogTitleTv.text = FIRST_EMOTION
                remindDialogHappyAiv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_mint_fiter_happy))
                remindDialogWhatAiv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_mint_fiter_what))
                remindDialogSadAiv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_mint_fiter_sad))
            }

            pomeRemindBottomSheetDialog.show()
        }

        // 돌아본 감정 선택
        binding.remindReviewLastEmotionCl.setOnClickListener {
            pomeRemindBottomSheetDialogBinding.apply {
                remindDialogTitleTv.text = LAST_EMOTION
                remindDialogHappyAiv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_pink_fiter_happy))
                remindDialogWhatAiv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_pink_fiter_what))
                remindDialogSadAiv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_pink_fiter_sad))
            }

            pomeRemindBottomSheetDialog.show()
        }

        // bottomSheetDialog 감정 선택
        // Happy
        pomeRemindBottomSheetDialogBinding.remindDialogHappyContainerCl.setOnClickListener {
            if(isFirstEmotion()) { // 처음 감정인 경우
                Toast.makeText(requireContext(), "$FIRST_EMOTION : happy", Toast.LENGTH_SHORT).show()
            } else if(isLastEmotion()) { // 돌아본 감정인 경우
                Toast.makeText(requireContext(), "$LAST_EMOTION : happy", Toast.LENGTH_SHORT).show()
            } else { // 어떠한 경우도 아닌 경우 토스트로 에러를 알림
                Toast.makeText(requireContext(), "감정 선택 Error가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            pomeRemindBottomSheetDialog.dismiss()
        }

        // What
        pomeRemindBottomSheetDialogBinding.remindDialogWhatContainerCl.setOnClickListener {
            if(isFirstEmotion()) { // 처음 감정인 경우
                Toast.makeText(requireContext(), "$FIRST_EMOTION : what", Toast.LENGTH_SHORT).show()
            } else if(isLastEmotion()) { // 돌아본 감정인 경우
                Toast.makeText(requireContext(), "$LAST_EMOTION : what", Toast.LENGTH_SHORT).show()
            } else { // 어떠한 경우도 아닌 경우 토스트로 에러를 알림
                Toast.makeText(requireContext(), "감정 선택 Error가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            pomeRemindBottomSheetDialog.dismiss()
        }

        // Sad
        pomeRemindBottomSheetDialogBinding.remindDialogSadContainerCl.setOnClickListener {
            if(isFirstEmotion()) { // 처음 감정인 경우
                Toast.makeText(requireContext(), "$FIRST_EMOTION : sad", Toast.LENGTH_SHORT).show()
            } else if(isLastEmotion()) { // 돌아본 감정인 경우
                Toast.makeText(requireContext(), "$LAST_EMOTION : sad", Toast.LENGTH_SHORT).show()
            } else { // 어떠한 경우도 아닌 경우 토스트로 에러를 알림
                Toast.makeText(requireContext(), "감정 선택 Error가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            pomeRemindBottomSheetDialog.dismiss()
        }
    }

    // 직관성을 높이기 위해 두 가지 메소드로 구현
    private fun isFirstEmotion() : Boolean = pomeRemindBottomSheetDialogBinding.remindDialogTitleTv.text == FIRST_EMOTION
    private fun isLastEmotion() : Boolean = pomeRemindBottomSheetDialogBinding.remindDialogTitleTv.text == LAST_EMOTION
}