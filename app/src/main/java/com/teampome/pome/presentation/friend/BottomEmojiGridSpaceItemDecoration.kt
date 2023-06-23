package com.teampome.pome.presentation.friend

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.util.common.CommonUtil

class BottomEmojiGridSpaceItemDecoration(private val spanCount: Int): RecyclerView.ItemDecoration() {
    // 3, 20
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val column = position % spanCount

        val spaceInPx = CommonUtil.dpToPx(parent.context, 10)

        outRect.top = spaceInPx

        if (column == 0) {
            outRect.left = spaceInPx
        } else if (column == 1) {
            outRect.left = spaceInPx
        } else if (column == 2) {
            outRect.left = spaceInPx
        }
    }
}
