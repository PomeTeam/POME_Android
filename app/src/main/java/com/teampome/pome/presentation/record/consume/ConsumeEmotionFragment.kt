package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    }

    override fun initListener() {
        binding.consumeEmotionBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
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
}