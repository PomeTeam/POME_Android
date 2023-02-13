package com.teampome.pome.presentation.record.consume

import android.os.Bundle
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
import com.teampome.pome.databinding.FragmentConsumeEmotionBinding
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.Emotion
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.ConsumeEmotionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsumeEmotionFragment : BaseFragment<FragmentConsumeEmotionBinding>(R.layout.fragment_consume_emotion) {
    private val viewModel: ConsumeEmotionViewModel by viewModels()
    private val navArgs: ConsumeEmotionFragmentArgs by navArgs()

    private var isHappySelected = false
    private var isWhatSelected = false
    private var isSadSelected = false
    
    private lateinit var category: GoalCategory
    private lateinit var consumeDate: String
    private var consumePrice: Long = 0
    private lateinit var consumeRecord: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        category = navArgs.consumeRecord.category
        consumeDate = navArgs.consumeRecord.date
        consumePrice = navArgs.consumeRecord.price
        consumeRecord = navArgs.consumeRecord.record
        
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
                        moveToRecord()
                    }
                ) {

                }
            }
        })
    }

    override fun initView() {

    }

    override fun initListener() {
        binding.consumeEmotionBackButtonIv.setOnClickListener {
            CommonUtil.showBackButtonDialog(
                requireContext(),
                "작성을 그만두시겠어요?",
                "지금까지 작성한 내용은 모두 사라져요",
                R.drawable.pen_mint,
                "그만둘래요",
                "이어서 쓸래요",
                {
                    moveToRecord()
                }
            ) {

            }
        }

        binding.consumeEmotionHappyContainerCl.setOnClickListener {
            clickEmotionButton("happy")
            updateCheckButtonState()
        }

        binding.consumeEmotionWhatContainerCl.setOnClickListener {
            clickEmotionButton("what")
            updateCheckButtonState()
        }

        binding.consumeEmotionSadContainerCl.setOnClickListener {
            clickEmotionButton("sad")
            updateCheckButtonState()
        }

        binding.consumeEmotionCheckButtonAcb.setOnClickListener {
            viewModel.writeConsumeRecord(
                CommonUtil.emotionToNum(viewModel.selectedEmotion.value ?: Emotion.HAPPY_EMOTION),
                category.goalId,
                consumeRecord,
                consumeDate,
                consumePrice,
                object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        hideLoading()
                    }
                }
            )
        }
        
        viewModel.writeConsumeRecordResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    hideLoading()
                    
                    moveToConsumeComplete()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }
    }

    private fun updateCheckButtonState() {
        if(isHappySelected || isWhatSelected || isSadSelected) {
            CommonUtil.enabledPomeBtn(binding.consumeEmotionCheckButtonAcb)
        } else {
            CommonUtil.disabledPomeBtn(binding.consumeEmotionCheckButtonAcb)
        }
    }

    private fun clickEmotionButton(
        selectedItem: String
    ) {
        binding.apply {
            when(selectedItem) {
                "happy" -> {
                    if(!isHappySelected) {
                        changeSelectedEmotionView(consumeEmotionHappyBackgroundIv, consumeEmotionHappyTv)
                        changeUnSelectedEmotionView(consumeEmotionWhatBackgroundIv, consumeEmotionWhatTv)
                        changeUnSelectedEmotionView(consumeEmotionSadBackgroundIv, consumeEmotionSadTv)

                        isHappySelected = true
                        isWhatSelected = false
                        isSadSelected = false
                    } else {
                        changeUnSelectedEmotionView(consumeEmotionHappyBackgroundIv, consumeEmotionHappyTv)
                        isHappySelected = false
                    }
                    
                    viewModel.setEmotion(Emotion.HAPPY_EMOTION)
                }
                "what" -> {
                    if(!isWhatSelected) {
                        changeUnSelectedEmotionView(consumeEmotionHappyBackgroundIv, consumeEmotionHappyTv)
                        changeSelectedEmotionView(consumeEmotionWhatBackgroundIv, consumeEmotionWhatTv)
                        changeUnSelectedEmotionView(consumeEmotionSadBackgroundIv, consumeEmotionSadTv)

                        isHappySelected = false
                        isWhatSelected = true
                        isSadSelected = false
                    } else {
                        changeUnSelectedEmotionView(consumeEmotionWhatBackgroundIv, consumeEmotionWhatTv)
                        isWhatSelected = false
                    }
                    
                    viewModel.setEmotion(Emotion.WHAT_EMOTION)
                }
                "sad" -> {
                    if(!isSadSelected) {
                        changeUnSelectedEmotionView(consumeEmotionHappyBackgroundIv, consumeEmotionHappyTv)
                        changeUnSelectedEmotionView(consumeEmotionWhatBackgroundIv, consumeEmotionWhatTv)
                        changeSelectedEmotionView(consumeEmotionSadBackgroundIv, consumeEmotionSadTv)

                        isHappySelected = false
                        isWhatSelected = false
                        isSadSelected = true
                    } else {
                        changeUnSelectedEmotionView(consumeEmotionSadBackgroundIv, consumeEmotionSadTv)
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
        backgroundImg.background = ResourcesCompat.getDrawable(resources, R.drawable.bright_mint_circle_background, null)
        descText.setTextColor(resources.getColor(R.color.main, null))
    }

    private fun changeUnSelectedEmotionView(
        backgroundImg: ImageView,
        descText: TextView
    ) {
        backgroundImg.background = ResourcesCompat.getDrawable(resources, R.drawable.grey0_circle_background, null)
        descText.setTextColor(resources.getColor(R.color.grey_7, null))
    }

    private fun moveToConsumeComplete() {
        val action = ConsumeEmotionFragmentDirections.actionConsumeEmotionFragmentToConsumeCompleteFragment()

        findNavController().navigate(action)
    }

    private fun moveToRecord() {
        val action = ConsumeEmotionFragmentDirections.actionConsumeEmotionFragmentToRecordFragment()

        findNavController().navigate(action)
    }
}