package com.teampome.pome.util

import android.view.View
import com.teampome.pome.util.base.OnSingleClickListener

fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val oneClick = OnSingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(oneClick)
}