package com.teampome.pome.presentation.record.leave

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentLeaveEmotionBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.Emotion
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.LeaveEmotionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaveEmotionFragment : BaseFragment<FragmentLeaveEmotionBinding>(R.layout.fragment_leave_emotion) {
    private val args: LeaveEmotionFragmentArgs by navArgs()
    private val viewModel: LeaveEmotionViewModel by viewModels()

    private var isHappySelected = false
    private var isWhatSelected = false
    private var isSadSelected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CommonUtil.showBackButtonDialog(
                    requireContext(),
                    "작성을 그만두시겠어요?",
                    "지금까지 작성한 내용은 모두 사라져요",
                    R.drawable.pen_mint,
                    "그만둘래요",
                    "이어서 쓸래요",
                    {
                        findNavController().popBackStack()
                    }
                ) {

                }
            }
        })
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.leaveEmotionBackButtonIv.setOnClickListener {
            CommonUtil.showBackButtonDialog(
                requireContext(),
                "작성을 그만두시겠어요?",
                "지금까지 작성한 내용은 모두 사라져요",
                R.drawable.pen_mint,
                "그만둘래요",
                "이어서 쓸래요",
                {
                    findNavController().popBackStack()
                }
            ) {

            }
        }

        binding.leaveEmotionHappyContainerCl.setOnClickListener {
            clickEmotionButton("happy")
            updateCheckButtonState()
        }

        binding.leaveEmotionWhatContainerCl.setOnClickListener {
            clickEmotionButton("what")
            updateCheckButtonState()
        }

        binding.leaveEmotionSadContainerCl.setOnClickListener {
            clickEmotionButton("sad")
            updateCheckButtonState()
        }

        binding.leaveEmotionCheckButtonAcb.setOnClickListener {
            viewModel.writeSecondEmotion(
                args.recordId,
                CommonUtil.emotionToNum(viewModel.selectedEmotion.value ?: Emotion.HAPPY_EMOTION),
                object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        
        viewModel.writeSecondEmotionResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "두번째 감정 기록이 완료됐어요", Toast.LENGTH_SHORT).show()
                    hideLoading()
                    findNavController().popBackStack()
                }
                is ApiResponse.Failure -> {
                    Log.e("writeSecondEmotion", "write second emotion error $it")
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }
    }

    private fun updateCheckButtonState() {
        if(isHappySelected || isWhatSelected || isSadSelected) {
            CommonUtil.enabledPomeBtn(binding.leaveEmotionCheckButtonAcb)
        } else {
            CommonUtil.disabledPomeBtn(binding.leaveEmotionCheckButtonAcb)
        }
    }

    private fun clickEmotionButton(
        selectedItem: String
    ) {
        binding.apply {
            when(selectedItem) {
                "happy" -> {
                    if(!isHappySelected) {
                        changeSelectedEmotionView(leaveEmotionHappyBackgroundIv, leaveEmotionHappyTv)
                        changeUnSelectedEmotionView(leaveEmotionWhatBackgroundIv, leaveEmotionWhatTv)
                        changeUnSelectedEmotionView(leaveEmotionSadBackgroundIv, leaveEmotionSadTv)

                        isHappySelected = true
                        isWhatSelected = false
                        isSadSelected = false
                    } else {
                        changeUnSelectedEmotionView(leaveEmotionHappyBackgroundIv, leaveEmotionHappyTv)
                        isHappySelected = false
                    }

                    viewModel.setEmotion(Emotion.HAPPY_EMOTION)
                }
                "what" -> {
                    if(!isWhatSelected) {
                        changeUnSelectedEmotionView(leaveEmotionHappyBackgroundIv, leaveEmotionHappyTv)
                        changeSelectedEmotionView(leaveEmotionWhatBackgroundIv, leaveEmotionWhatTv)
                        changeUnSelectedEmotionView(leaveEmotionSadBackgroundIv, leaveEmotionSadTv)

                        isHappySelected = false
                        isWhatSelected = true
                        isSadSelected = false
                    } else {
                        changeUnSelectedEmotionView(leaveEmotionWhatBackgroundIv, leaveEmotionWhatTv)
                        isWhatSelected = false
                    }

                    viewModel.setEmotion(Emotion.WHAT_EMOTION)
                }
                "sad" -> {
                    if(!isSadSelected) {
                        changeUnSelectedEmotionView(leaveEmotionHappyBackgroundIv, leaveEmotionHappyTv)
                        changeUnSelectedEmotionView(leaveEmotionWhatBackgroundIv, leaveEmotionWhatTv)
                        changeSelectedEmotionView(leaveEmotionSadBackgroundIv, leaveEmotionSadTv)

                        isHappySelected = false
                        isWhatSelected = false
                        isSadSelected = true
                    } else {
                        changeUnSelectedEmotionView(leaveEmotionSadBackgroundIv, leaveEmotionSadTv)
                        isSadSelected = false
                    }

                    viewModel.setEmotion(Emotion.SAD_EMOTION)
                }
            }
        }
    }

    private fun changeSelectedEmotionView(
        backgroundImg: ImageView,
        descText: TextView
    ) {
        backgroundImg.background = ResourcesCompat.getDrawable(resources, R.drawable.bright_pink_circle_background, null)
        descText.setTextColor(resources.getColor(R.color.pink_100, null))
    }

    private fun changeUnSelectedEmotionView(
        backgroundImg: ImageView,
        descText: TextView
    ) {
        backgroundImg.background = ResourcesCompat.getDrawable(resources, R.drawable.grey0_circle_background, null)
        descText.setTextColor(resources.getColor(R.color.grey_7, null))
    }
}