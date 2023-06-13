package com.teampome.pome.util.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.teampome.pome.R
import com.teampome.pome.util.common.CommonUtil

class PomeBigGoalCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : PomeSmallGoalCardView(context, attrs) {
    var useAmount: String? = ""
        set(value) {
            field = value?.let { context.getString(R.string.use_price_text, CommonUtil.makePriceStyle(value)) } ?: value

            field?.let {
                binding.goalCardUsedAmountAtv.text = it
            }
        }

    var goalAmount: String? = ""
        set(value) {
            field = value?.let { context.getString(R.string.goal_price_text, CommonUtil.makePriceStyle(value)) } ?: value

            field?.let {
                binding.goalCardAmountAtv.text = it
            }
        }

    var progress: Int = 0
        set(value) {
            field = value

            if(value > 100) {
                binding.goalCardAmountProgressAsb.progress = 100
                binding.goalCardProgressTextTv.text = context.getString(R.string.record_progress_over)

                binding.goalCardAmountProgressAsb.progressDrawable = AppCompatResources.getDrawable(context, R.drawable.pome_seekbar_over_custom)
                binding.goalCardAmountProgressAsb.thumb = AppCompatResources.getDrawable(context, R.drawable.pome_seekbar_custom_over_thumb)
            } else {
                binding.goalCardAmountProgressAsb.progress = value
                binding.goalCardProgressTextTv.text = context.getString(R.string.record_progress_percent, value)

                binding.goalCardAmountProgressAsb.progressDrawable = AppCompatResources.getDrawable(context, R.drawable.pome_seekbar_custom)
                binding.goalCardAmountProgressAsb.thumb = AppCompatResources.getDrawable(context, R.drawable.pome_seekbar_custom_thumb)
            }

            binding.goalCardAmountProgressAsb.apply {
                isEnabled = false

                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        val thumbBounds = thumb.bounds
                        val progressTextWidth = binding.goalCardProgressTextTv.width

//                        Log.d("progress", "thumbBounds center : ${thumbBounds.exactCenterX()}, progressTextWidth : $progressTextWidth")

                        // thumb의 중간 - (progressTv 길이 / 2) -1f => 1f는 살짝 왼쪽으로 조정하는 값
                        binding.goalCardProgressTextTv.x = thumbBounds.exactCenterX() - (progressTextWidth.toFloat() / 2f) - 1f
                    }
                })
            }
        }

    /*
        MyPage에서 완료된 목표를 보여주기 위한 변수
        만약 true이면 progress 값이 100초과면 실패, 100이하면 성공 태그를 붙인다.
     */
    var isCompleteGoalCard: Boolean? = false
        set(value) {
            field = value

            if(field == true) {
                binding.goalCardMoreIv.visibility = View.VISIBLE

                if(progress > 100) { // 실패
                    binding.goalCardDayChipTv.text = resources.getString(R.string.regret_text)
                    binding.goalCardDayChipTv.setTextColor(resources.getColor(R.color.white, null))
                    binding.goalCardDayChipTv.background = ResourcesCompat.getDrawable(resources, R.drawable.item_red_r4_background, null)
                } else { // 성공
                    binding.goalCardDayChipTv.text = resources.getString(R.string.success_text)
                    binding.goalCardDayChipTv.setTextColor(resources.getColor(R.color.white, null))
                    binding.goalCardDayChipTv.background = ResourcesCompat.getDrawable(resources, R.drawable.item_main_r4_background, null)
                }
            }
        }

    init {
        binding.goalCardBigContainerCl.visibility = VISIBLE

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PomeGoalCardView,
            0, 0
        )

        try {
            useAmount = attr.getString(R.styleable.PomeGoalCardView_setUsedAmount) ?: ""
            goalAmount = attr.getString(R.styleable.PomeGoalCardView_setGoalAmount) ?: ""
            progress = attr.getInteger(R.styleable.PomeGoalCardView_setPomeProgress, 0)
            isCompleteGoalCard = attr.getBoolean(R.styleable.PomeGoalCardView_setCompleteGoalCard, false)

            Log.d("test", "progress : $progress")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            attr.recycle()
        }
    }

    fun setMoreBtnClickListener(onClick: () -> Unit) {
        binding.goalCardMoreIv.setOnClickListener {
            onClick.invoke()
        }
    }
}