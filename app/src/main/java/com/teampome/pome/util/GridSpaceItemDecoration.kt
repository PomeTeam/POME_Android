package com.teampome.pome.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//Grid Space Setting
class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {
    //spanCount 2
    //space : 10
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val column = position % spanCount + 1      // 1부터 시작
        // position + 2를 통해서 위의 헤더를 제외한 나머지 부터 top 과 left 에 공간을 준다.
        // 또한, 헤더 있는 값을 제외한 나머지 컬럼 ( right 쪽에 있는 아이템 ) 들의 right 쪽에 공간을 준다.
        if (position+2 > spanCount){
            outRect.top = space
            outRect.left = space
            if(column == spanCount-1){
                outRect.right = space
            }
        }
    }

}