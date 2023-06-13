package com.teampome.pome.presentation.mypage.recyclerview.goal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemMypageGoalCardBinding
import com.teampome.pome.model.goal.GoalData

class MyPageGoalAdapter(
    private val onMoreBtnClick: (GoalData) -> Unit
) : ListAdapter<GoalData, MyPageGoalAdapter.MyPageGoalViewHolder>(GoalDataDiffCallback()) {

    private lateinit var binding: ItemMypageGoalCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageGoalViewHolder {
        binding = ItemMypageGoalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyPageGoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageGoalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyPageGoalViewHolder(val binding: ItemMypageGoalCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: GoalData) {
            binding.goalData = data
            binding.mypageGoalCardPbgcv.setMoreBtnClickListener {
                onMoreBtnClick.invoke(data)
            }

            binding.executePendingBindings()
        }
    }
}

class GoalDataDiffCallback: DiffUtil.ItemCallback<GoalData>() {
    override fun areItemsTheSame(oldItem: GoalData, newItem: GoalData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GoalData, newItem: GoalData): Boolean {
        return oldItem == newItem
    }
}