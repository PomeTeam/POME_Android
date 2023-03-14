package com.teampome.pome.presentation.record.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemGoalCardBinding
import com.teampome.pome.databinding.ItemLeaveEmotionCardBinding
import com.teampome.pome.databinding.ItemRecordContentsCardBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.presentation.record.recyclerview.OnMoreItemClickListener
import com.teampome.pome.presentation.record.recyclerview.OnRecordItemClickListener
import com.teampome.pome.presentation.record.recyclerview.viewholder.GoalCardViewHolder
import com.teampome.pome.presentation.record.recyclerview.viewholder.LeaveEmotionCardViewHolder
import com.teampome.pome.presentation.record.recyclerview.viewholder.RecordCardViewHolder
import com.teampome.pome.util.common.OnItemClickListener

sealed class RecordViewType(val type: Int) {
    object Goal: RecordViewType(0)
    object OneWeek: RecordViewType(1)
    object Contents: RecordViewType(2)
//    OneWeek(0), Contents(1)
}

class RecordContentsCardAdapter : PagingDataAdapter<RecordData, RecyclerView.ViewHolder>(
    RecordContentsCardDiffCallback()
) {
    // 절대 Adapter 내부에 lateinit var을 넣지 말자.
    // bind를 lateinit하고 onCreateViewHolder에서 재사용하고 있어 메모리 누수가 발생됨
    // ex) RecyclerView에서 아이템이 추가될 때 마다 Binding 인스턴스가 할당되고 이전에 할당된 인스턴스가 GC로 회수되지 않을 가능성이 매우 큼
    private var moreRecordItemClickListener: OnMoreItemClickListener? = null
    private var recordBodyClickListener: OnRecordItemClickListener? = null
    private var leaveEmotionCardClickListener: OnItemClickListener? = null
    private var moreGoalItemClickListener: OnItemClickListener? = null
    private var noGoalCardClickListener: OnItemClickListener? = null
    private var goalCompleteClickListener: OnItemClickListener? = null

    fun setOnMoreRecordItemClickListener(listener: OnMoreItemClickListener) {
        moreRecordItemClickListener = listener
    }

    fun setOnRecordBodyClickListener(listener: OnRecordItemClickListener) {
        recordBodyClickListener = listener
    }

    fun setOnLeaveEmotionCardClickListener(listener: OnItemClickListener) {
        leaveEmotionCardClickListener = listener
    }

    fun setOnMoreGoalItemClickListener(listener: OnItemClickListener) {
        moreGoalItemClickListener = listener
    }

    fun setOnNoGoalCardClickListener(listener: OnItemClickListener) {
        noGoalCardClickListener = listener
    }

    fun setOnGoalCompleteClickListener(listener: OnItemClickListener) {
        goalCompleteClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        val data = getItem(position)

        data?.let { recordData ->
            recordData.viewType?.let { viewType ->
                return viewType.type
            } ?: run {
                return RecordViewType.Contents.type
            }
        } ?: run {
            return RecordViewType.Contents.type
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when(viewType) {
            RecordViewType.Goal.type -> {
                val binding = ItemGoalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return GoalCardViewHolder(binding, moreGoalItemClickListener, noGoalCardClickListener, goalCompleteClickListener)
            }

            RecordViewType.OneWeek.type -> {
                val binding = ItemLeaveEmotionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return LeaveEmotionCardViewHolder(binding, leaveEmotionCardClickListener)
            }

            RecordViewType.Contents.type -> {
                val binding = ItemRecordContentsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return RecordCardViewHolder(binding, moreRecordItemClickListener, recordBodyClickListener)
            }

            else -> {
                assert(false) // 여기로 떨어지면 안됨 -> 따로 처리 필요
                val binding = ItemRecordContentsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return RecordCardViewHolder(binding, moreRecordItemClickListener, recordBodyClickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is GoalCardViewHolder -> {
                getItem(position)?.let {
                    holder.bind(it.goalDetail, it.goalState)
                }
            }

            is LeaveEmotionCardViewHolder -> {
                getItem(position)?.let {
                    holder.bind(it.oneWeekCount ?: 0)
                }
            }

            is RecordCardViewHolder -> {
                getItem(position)?.let {
                    holder.bind(it)
                }
            }
        }
    }
}

class RecordContentsCardDiffCallback : DiffUtil.ItemCallback<RecordData>() {
    override fun areItemsTheSame(oldItem: RecordData, newItem: RecordData): Boolean {
        return if(oldItem.id != null && newItem.id != null) {
            oldItem.id == newItem.id
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItem: RecordData, newItem: RecordData): Boolean {
        return oldItem == newItem
    }
}