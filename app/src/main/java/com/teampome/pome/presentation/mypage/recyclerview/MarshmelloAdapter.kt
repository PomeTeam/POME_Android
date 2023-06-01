package com.teampome.pome.presentation.mypage.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teampome.pome.R
import com.teampome.pome.databinding.ItemMypageCardBinding
import com.teampome.pome.model.mytab.MyTabMarshmello

/// 마이탭 마시멜로우 어댑터
class MarshmelloAdapter :
    ListAdapter<MyTabMarshmello, MarshmelloAdapter.MyTabMarshmelloViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTabMarshmelloViewHolder {
        return MyTabMarshmelloViewHolder(
            ItemMypageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: MyTabMarshmelloViewHolder, position: Int) {
        val marshmello = currentList[position]

        holder.bind(marshmello)
    }

    inner class MyTabMarshmelloViewHolder(
        private val binding: ItemMypageCardBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(myTabMarshmello: MyTabMarshmello) {
            binding.myTabMarshmello = myTabMarshmello

            when (myTabMarshmello.marshmelloName) {
                "record" -> binding.mypageCardRecordTv.text = "기록말랑"
                "emotion" -> binding.mypageCardRecordTv.text = "공감말랑"
                "growth" -> binding.mypageCardRecordTv.text = "발전말랑"
                "honest" -> binding.mypageCardRecordTv.text = "솔직말랑"
            }

            if (myTabMarshmello.marshmelloLv == 0) {
                Glide.with(context).load(R.drawable.marshmallow_lock)
                    .into(binding.ivArticleImage)
                binding.mypageCardLevelTv.text = "Lv.1"
                binding.mypageCardLevelTv.setTextColor(
                    ContextCompat.getColor(
                        binding.ivArticleImage.context,
                        R.color.grey_4
                    )
                )
                binding.mypageCardLevelCl.setBackgroundResource(R.drawable.item_gray_oval)
                binding.mypageCardRecordCl.setBackgroundResource(R.drawable.item_grey1_r8_background)
                binding.mypageCardRecordTv.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.grey_5
                    )
                )
            } else if (myTabMarshmello.marshmelloLv == 1) {
                binding.mypageCardLevelTv.text = "Lv.1"
                if (myTabMarshmello.marshmelloName == "record") {
                    //기록
                    Glide.with(context).load(R.drawable.marshmallow_level_1_pink)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "emotion") {
                    //공감
                    Glide.with(context).load(R.drawable.marshmallow_level_1_blue)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "growth") {
                    //발전
                    Glide.with(context).load(R.drawable.marshmallow_level_1_yellow)
                        .into(binding.ivArticleImage)
                } else {
                    //솔직
                    Glide.with(context).load(R.drawable.marshmallow_level_1_mint)
                        .into(binding.ivArticleImage)
                }
            } else if (myTabMarshmello.marshmelloLv == 2) {
                binding.mypageCardLevelTv.text = "Lv.2"
                if (myTabMarshmello.marshmelloName == "record") {
                    Glide.with(context).load(R.drawable.marshmallow_level_2_pink)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "emotion") {
                    Glide.with(context).load(R.drawable.marshmallow_level_2_blue)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "growth") {
                    Glide.with(context).load(R.drawable.marshmallow_level_2_yellow)
                        .into(binding.ivArticleImage)
                } else {
                    Glide.with(context).load(R.drawable.marshmallow_level_2_mint)
                        .into(binding.ivArticleImage)
                }
            } else if (myTabMarshmello.marshmelloLv == 3) {
                binding.mypageCardLevelTv.text = "Lv.3"
                if (myTabMarshmello.marshmelloName == "record") {
                    Glide.with(context).load(R.drawable.marshmallow_level_3_pink)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "emotion") {
                    Glide.with(context).load(R.drawable.marshmallow_level_3_blue)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "growth") {
                    Glide.with(context).load(R.drawable.marshmallow_level_3_yellow)
                        .into(binding.ivArticleImage)
                } else {
                    Glide.with(context).load(R.drawable.marshmallow_level_3_mint)
                        .into(binding.ivArticleImage)
                }
            } else {
                binding.mypageCardLevelTv.text = "Lv.4"
                if (myTabMarshmello.marshmelloName == "record") {
                    Glide.with(context).load(R.drawable.marshmallow_level_4_pink)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "emotion") {
                    Glide.with(context).load(R.drawable.marshmallow_level_4_blue)
                        .into(binding.ivArticleImage)
                } else if (myTabMarshmello.marshmelloName == "growth") {
                    Glide.with(context).load(R.drawable.marshmallow_level_4_yellow)
                        .into(binding.ivArticleImage)
                } else {
                    Glide.with(context).load(R.drawable.marshmallow_level_4_mint)
                        .into(binding.ivArticleImage)
                }
            }
        }
    }

    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<MyTabMarshmello>() {
            override fun areItemsTheSame(
                oldItem: MyTabMarshmello,
                newItem: MyTabMarshmello
            ): Boolean {
                return oldItem.marshmelloName == newItem.marshmelloName
            }

            override fun areContentsTheSame(
                oldItem: MyTabMarshmello,
                newItem: MyTabMarshmello
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}