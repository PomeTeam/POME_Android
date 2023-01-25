package com.teampome.pome.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.teampome.pome.R
import com.teampome.pome.databinding.PomeGoalCardBinding

open class PomeSmallGoalCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : MaterialCardView(context, attrs) {
    protected var binding: PomeGoalCardBinding
    var goalText: String? = ""
        set(value) {
            field = value

            if(value.isNullOrEmpty()) {
                makeNoGoalView()
            } else {
                binding.goalCardTextTv.text = value
                makeGoalView()
            }
        }
    var isPrivate: Boolean = false
        set(value) {
            field = value
            binding.goalCardPublicChipTv.text = if(value) "공개" else "비공개"
        }
    var remainDays: Int = 0
        set(value) {
            field = value
            binding.goalCardDayChipTv.text = resources.getString(R.string.card_day_chip_text, value)
        }

    init {
        binding = PomeGoalCardBinding.inflate(LayoutInflater.from(context), null, false)

        this.addView(binding.root)

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PomeGoalCardView,
            0, 0
        )

        try {
            goalText = attr.getString(R.styleable.PomeGoalCardView_setGoalText)
            isPrivate = !attr.getBoolean(R.styleable.PomeGoalCardView_setPrivate, false)
            remainDays = attr.getInt(R.styleable.PomeGoalCardView_setRemainDays, 0)
        } finally {
            attr.recycle()
        }
    }

    private fun makeNoGoalView() {
        binding.goalCardEmptyIv.visibility = VISIBLE

        binding.goalCardTextTv.apply {
            text = resources.getString(R.string.remind_goal_card_no_text)
            setTextColor(resources.getColor(R.color.grey_5, null))
        }

        binding.goalCardPublicChipTv.visibility = GONE
        binding.goalCardDayChipTv.visibility = GONE
    }

    private fun makeGoalView() {
        binding.goalCardEmptyIv.visibility = GONE

        binding.goalCardTextTv.setTextColor(resources.getColor(R.color.grey_7, null))


        binding.goalCardPublicChipTv.visibility = VISIBLE
        binding.goalCardDayChipTv.visibility = VISIBLE
    }
}