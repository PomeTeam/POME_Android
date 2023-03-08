package com.teampome.pome.presentation.record

import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemGoalCardBinding
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.util.OnItemClickListener

class GoalCardViewHolder(
    private val binding: ItemGoalCardBinding,
    private val moreItemClickListener: OnItemClickListener?,
    private val noGoalCardClickListener: OnItemClickListener?,
    private val goalCompleteClickListener: OnItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(goalData: GoalData?, goalState: GoalState?) {
        binding.recordGoalMoreAiv.setOnClickListener {
            moreItemClickListener?.itemClick()
        }

        binding.recordNoGoalSubtitleContainerCl.setOnClickListener {
            noGoalCardClickListener?.itemClick()
        }

        binding.recordGoalCompleteCl.setOnClickListener {
            goalCompleteClickListener?.itemClick()
        }

        binding.goalDetails = goalData
        binding.currentGoalState = goalState ?: GoalState.Empty

        binding.executePendingBindings()
    }
}