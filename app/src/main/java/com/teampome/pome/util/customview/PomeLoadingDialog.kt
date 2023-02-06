package com.teampome.pome.util.customview

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.bumptech.glide.Glide
import com.teampome.pome.R
import com.teampome.pome.databinding.PomeLoadingDialogBinding

class PomeLoadingDialog constructor(context: Context) : Dialog(context){
    lateinit var binding: PomeLoadingDialogBinding

    init {
        // dialog no title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = PomeLoadingDialogBinding.inflate(LayoutInflater.from(context), null, false)
        setContentView(binding.root)

        Glide.with(context).load(R.drawable.loading).into(binding.pomeLoadingIv)
    }
}