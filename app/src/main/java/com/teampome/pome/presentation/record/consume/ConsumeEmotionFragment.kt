package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentConsumeEmotionBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment

class ConsumeEmotionFragment : BaseFragment<FragmentConsumeEmotionBinding>(R.layout.fragment_consume_emotion) {
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
            moveToConsumeComplete()
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