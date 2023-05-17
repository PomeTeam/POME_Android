package com.teampome.pome.presentation.mypage.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.util.common.CommonUtil

class GridSpaceItemDecoration(private val spanCount: Int): RecyclerView.ItemDecoration() {
    // 3, 20
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val column = position % spanCount

        val spaceInPx = CommonUtil.dpToPx(parent.context, 5)

        if (column == 0) {
            outRect.right = spaceInPx
        } else if (column == 1) {
            outRect.left = spaceInPx
        }
    }
}
