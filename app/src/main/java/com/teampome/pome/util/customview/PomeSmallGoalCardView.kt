package com.teampome.pome.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.teampome.pome.R
import com.teampome.pome.databinding.PomeSmallGoalCardviewBinding

class PomeSmallGoalCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : MaterialCardView(context, attrs) {
    private var binding: PomeSmallGoalCardviewBinding
    var goalText: String? = ""
        set(value) {
            field = value

            if(value.isNullOrEmpty()) {
                makeNoGoalView()
            } else {
                binding.smallCardGoalTv.text = value
                makeGoalView()
            }
        }
    var isPrivate: Boolean = false
        set(value) {
            field = value
            binding.smallCardPublicChipTv.text = if(value) "공개" else "비공개"
        }
    var remainDays: Int = 0
        set(value) {
            field = value
            binding.smallCardDayChipTv.text = resources.getString(R.string.card_day_chip_text, value)
        }

    init {
        binding = PomeSmallGoalCardviewBinding.inflate(LayoutInflater.from(context), null, false)

        addView(binding.root)

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PomeSmallGoalCardView,
            0, 0
        )

        try {
            goalText = attr.getString(R.styleable.PomeSmallGoalCardView_setGoalText)
            isPrivate = !attr.getBoolean(R.styleable.PomeSmallGoalCardView_setPrivate, false)
//            remainDays = attr.getInt(R.styleable.PomeSmallGoalCardView_setDay, 0)
            remainDays = 0
        } finally {
            attr.recycle()
        }
    }

    private fun makeNoGoalView() {
        binding.smallCardGoalEmptyIv.visibility = VISIBLE

        binding.smallCardGoalTv.apply {
            text = resources.getString(R.string.remind_goal_card_no_text)
            setTextColor(resources.getColor(R.color.grey_5, null))
        }

        binding.smallCardPublicChipTv.visibility = GONE
        binding.smallCardDayChipTv.visibility = GONE
    }

    private fun makeGoalView() {
        binding.smallCardGoalEmptyIv.visibility = GONE

        binding.smallCardGoalTv.setTextColor(resources.getColor(R.color.grey_7, null))


        binding.smallCardPublicChipTv.visibility = VISIBLE
        binding.smallCardDayChipTv.visibility = VISIBLE
    }
}