package com.teampome.pome.presentation.mypage

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

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

        val spaceInPx = dpToPx(parent.context, 5)

        if (column == 0) {
            outRect.right = spaceInPx
        } else if (column == 1) {
            outRect.left = spaceInPx
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }
}
