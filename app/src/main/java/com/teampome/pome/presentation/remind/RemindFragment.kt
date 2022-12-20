package com.teampome.pome.presentation.remind

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRemindBinding
import com.teampome.pome.databinding.PomeRemindBottomSheetDialogBinding
import com.teampome.pome.model.RemindCategoryData
import com.teampome.pome.util.Constants.FIRST_EMOTION
import com.teampome.pome.util.Constants.LAST_EMOTION
import com.teampome.pome.viewmodel.RemindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemindFragment : BaseFragment<FragmentRemindBinding>(R.layout.fragment_remind) {

    // bottomSheetDialog
    private lateinit var pomeRemindBottomSheetDialogBinding: PomeRemindBottomSheetDialogBinding
    private lateinit var pomeRemindBottomSheetDialog: BottomSheetDialog

    // viewModel
    private val viewModel: RemindViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pomeRemindBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeRemindBottomSheetDialogBinding = PomeRemindBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        pomeRemindBottomSheetDialog.setContentView(pomeRemindBottomSheetDialogBinding.root)

        // RecyclerView adapter 설정
        binding.remindCategoryChipRv.adapter = RemindCategoryChipAdapter().apply {
            setOnItemClickListener(object : OnCategoryItemClickListener {
                override fun onCategoryItemClick(item: RemindCategoryData, position: Int) { // 선택된 카테고리 내용 확인
                    Toast.makeText(requireContext(), "item : $item, position : $position", Toast.LENGTH_SHORT).show()
                }
            })
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initListener() {

        // test data livedata listener
        viewModel.testRemindList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.remindTestItem = it.contentItems[0] // 일단 0번 데이터를 넣기
            }

            Log.d("test", "test data is $it")

            // 자체로 필터링해서 데이터를 집어넣자
            val testCategoryList: List<RemindCategoryData> = (it?.let { remindTestData ->
                remindTestData.contentItems.map { remindTestItem ->
                    RemindCategoryData(
                        remindTestItem.mainCategory
                    )
                }
            } ?: listOf(RemindCategoryData("···"))) // null이면 ··· 으로 => category가 없는 경우

            Log.d("test", "filter test data is $testCategoryList")

            (binding.remindCategoryChipRv.adapter as RemindCategoryChipAdapter).submitList(
                testCategoryList
            )
        }

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

        // bottomSheetDialog close
        pomeRemindBottomSheetDialogBinding.remindDialogCloseAiv.setOnClickListener {
            pomeRemindBottomSheetDialog.dismiss()
        }
    }

    // 직관성을 높이기 위해 두 가지 메소드로 구현
    private fun isFirstEmotion() : Boolean = pomeRemindBottomSheetDialogBinding.remindDialogTitleTv.text == FIRST_EMOTION
    private fun isLastEmotion() : Boolean = pomeRemindBottomSheetDialogBinding.remindDialogTitleTv.text == LAST_EMOTION
}