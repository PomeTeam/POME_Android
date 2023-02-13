package com.teampome.pome.util.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import com.teampome.pome.R
import com.teampome.pome.util.CommonUtil

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

            binding.goalCardAmountProgressAsb.progress = value
            binding.goalCardProgressTextTv.text = context.getString(R.string.record_progress_percent, value)

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

            Log.d("test", "progress : $progress")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            attr.recycle()
        }
    }
}