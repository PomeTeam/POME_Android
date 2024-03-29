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
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.presentation.record.recyclerview.adapter.RecordCategoryAdapter
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.common.Constants.FIRST_EMOTION
import com.teampome.pome.util.common.Constants.LAST_EMOTION
import com.teampome.pome.util.common.Emotion
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.RemindViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemindFragment : BaseFragment<FragmentRemindBinding>(R.layout.fragment_remind) {

    /*
         데이터는 항상 일관성 있게! => View의 data는 항상 ViewModel의 data를 바라보게 (dummy data x, 일관성 하락)
     */

    private val remindRecordsCoroutineErrorHandler: CoroutineErrorHandler = object : CoroutineErrorHandler {
        override fun onError(message: String) {
            Log.e("record", "getRemindRecords error $message")
        }
    }

    // bottomSheetDialog
    private lateinit var pomeRemindBottomSheetDialogBinding: PomeRemindBottomSheetDialogBinding
    private lateinit var pomeRemindBottomSheetDialog: BottomSheetDialog

    // viewModel
    private val viewModel: RemindViewModel by viewModels()

    private lateinit var recordData: RecordData
    private var currentCategory: String? = null
    private var currentCategoryPosition: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        makePomeBottomSheetDialog()

        viewModel.findEndGoals(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Log.e("error", "findAllGoalByUser error $message")
            }
        })

        // 카테고리 어댑터 설정
        binding.remindCategoryChipRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: GoalCategory, position: Int) {
                        currentCategory = item.name
                        currentCategoryPosition = position

                        viewModel.getRemindRecords(item.goalId, firstEmotion = null, secondEmotion = null, remindRecordsCoroutineErrorHandler)

                        binding.goalData = viewModel.goalDetails.value?.get(position)
                        binding.executePendingBindings()
                    }
                })
            }
    }

    override fun initListener() {
        viewModel.findEndGoalsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.content?.let { list ->
                        if(list.isNotEmpty()) currentCategoryPosition = 0
                        currentCategoryPosition?.let { pos ->
                            binding.goalData = list[pos]
                            binding.executePendingBindings()
                        }
                    } ?: run {
                        binding.goalData = null
                        binding.executePendingBindings()
                    }

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

        viewModel.goalCategory.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                // 초기에 category를 받으면 0번을 기반으로 데이터 초기화
                currentCategory = it[0]!!.name
                currentCategoryPosition = 0

                // 카테고리 데이터 받은 후 목표 가져오는 작업 진행
                viewModel.getRemindRecords(it[0]!!.goalId, null, null, object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Log.e("record", "record error $message")
                    }
                })

                (binding.remindCategoryChipRv.adapter as RecordCategoryAdapter).submitList(
                    it
                )

                binding.goalCategoryList = it
                binding.executePendingBindings()
            }
        }

        viewModel.goalDetails.observe(viewLifecycleOwner) {

        }

        viewModel.getRemindRecordsResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.let { recordData ->
                        binding.remindReviewRv.adapter = RemindContentsCardAdapter().apply {
                            submitList(recordData.content)
                        }

                        binding.recordList = recordData.content
                        binding.executePendingBindings()
                    }

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
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
                binding.firstEmotion = Emotion.HAPPY_EMOTION
            } else if(isLastEmotion()) { // 돌아본 감정인 경우
                binding.lastEmotion = Emotion.HAPPY_EMOTION
            } else { // 어떠한 경우도 아닌 경우 토스트로 에러를 알림
                Toast.makeText(requireContext(), "감정 선택 Error가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            binding.executePendingBindings()

            binding.firstEmotion?.let{
                safeGetRemindRecordsCall(CommonUtil.emotionToNum(binding.firstEmotion), CommonUtil.emotionToNum(binding.lastEmotion))
            }

            pomeRemindBottomSheetDialog.dismiss()
        }

        // What
        pomeRemindBottomSheetDialogBinding.remindDialogWhatContainerCl.setOnClickListener {
            if(isFirstEmotion()) { // 처음 감정인 경우
                binding.firstEmotion = Emotion.WHAT_EMOTION
            } else if(isLastEmotion()) { // 돌아본 감정인 경우
                binding.lastEmotion = Emotion.WHAT_EMOTION
            } else { // 어떠한 경우도 아닌 경우 토스트로 에러를 알림
                Toast.makeText(requireContext(), "감정 선택 Error가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            binding.executePendingBindings()

            safeGetRemindRecordsCall(CommonUtil.emotionToNum(binding.firstEmotion), CommonUtil.emotionToNum(binding.lastEmotion))

            pomeRemindBottomSheetDialog.dismiss()
        }

        // Sad
        pomeRemindBottomSheetDialogBinding.remindDialogSadContainerCl.setOnClickListener {
            if(isFirstEmotion()) { // 처음 감정인 경우
                binding.firstEmotion = Emotion.SAD_EMOTION
            } else if(isLastEmotion()) { // 돌아본 감정인 경우
                binding.lastEmotion = Emotion.SAD_EMOTION
            } else { // 어떠한 경우도 아닌 경우 토스트로 에러를 알림
                Toast.makeText(requireContext(), "감정 선택 Error가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }

            binding.executePendingBindings()

            safeGetRemindRecordsCall(CommonUtil.emotionToNum(binding.firstEmotion), CommonUtil.emotionToNum(binding.lastEmotion))

            pomeRemindBottomSheetDialog.dismiss()
        }

        // bottomSheetDialog close
        pomeRemindBottomSheetDialogBinding.remindDialogCloseAiv.setOnClickListener {
            pomeRemindBottomSheetDialog.dismiss()
        }

        // 초기화 버튼 클릭시
        binding.remindReviewResetContainterCl.setOnClickListener {
            binding.firstEmotion = null
            binding.lastEmotion = null

            safeGetRemindRecordsCall(null, null)

            binding.executePendingBindings()
        }
    }

    private fun makePomeBottomSheetDialog() {
        pomeRemindBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeRemindBottomSheetDialogBinding = PomeRemindBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        pomeRemindBottomSheetDialog.setContentView(pomeRemindBottomSheetDialogBinding.root)
    }

    private fun safeGetRemindRecordsCall(firstEmotion: Int?, secondEmotion: Int?) {
        currentCategoryPosition?.let { pos ->
            viewModel.goalCategory.value?.let{ list ->
                list[pos]?.let {
                    viewModel.getRemindRecords(it.goalId, firstEmotion, secondEmotion, remindRecordsCoroutineErrorHandler)
                }
            }
        }
    }

    // 직관성을 높이기 위해 두 가지 메소드로 구현
    private fun isFirstEmotion() : Boolean = pomeRemindBottomSheetDialogBinding.remindDialogTitleTv.text == FIRST_EMOTION
    private fun isLastEmotion() : Boolean = pomeRemindBottomSheetDialogBinding.remindDialogTitleTv.text == LAST_EMOTION
}