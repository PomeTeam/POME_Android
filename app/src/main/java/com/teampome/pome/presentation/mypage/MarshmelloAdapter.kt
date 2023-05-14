package com.teampome.pome.presentation.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teampome.pome.R
import com.teampome.pome.databinding.ItemMypageCardBinding
import com.teampome.pome.model.mytab.MyTabMarshmello

/// 마이탭 마시멜로우 어댑터
class MarshmelloAdapter(
    private val context : Context?
): ListAdapter<MyTabMarshmello, MarshmelloAdapter.MyTabMarshmelloViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTabMarshmelloViewHolder {
        return MyTabMarshmelloViewHolder(
            ItemMypageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: MyTabMarshmelloViewHolder, position: Int) {
        val marshmello = currentList[position]
        holder.bind(marshmello)

    }

    inner class MyTabMarshmelloViewHolder(
        private val binding : ItemMypageCardBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(myTabMarshmello: MyTabMarshmello){
            binding.myTabMarshmello = myTabMarshmello
            context?.let{
                when(myTabMarshmello.marshmelloName) {
                    "record" -> Glide.with(context).load(R.drawable.marshmallow_level_4_pink).into(binding.ivArticleImage)
                    "emotion" -> Glide.with(context).load(R.drawable.marshmallow_level_2_yellow).into(binding.ivArticleImage)
                    "growth" -> Glide.with(context).load(R.drawable.marshmallow_level_4_mint).into(binding.ivArticleImage)
                    "honest" -> Glide.with(context).load(R.drawable.ic_mashmellow).into(binding.ivArticleImage)
                    else -> Glide.with(context).load(R.drawable.ic_mashmellow).into(binding.ivArticleImage)
                }
            }
        }
    }

    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<MyTabMarshmello>(){
            override fun areItemsTheSame(oldItem: MyTabMarshmello, newItem: MyTabMarshmello): Boolean {
                return oldItem.marshmelloName == newItem.marshmelloName
            }

            override fun areContentsTheSame(oldItem: MyTabMarshmello, newItem: MyTabMarshmello): Boolean {
                return oldItem == newItem
            }
        }
    }
}