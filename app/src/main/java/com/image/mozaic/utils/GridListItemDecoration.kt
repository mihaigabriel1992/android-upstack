package com.image.mozaic.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.math.roundToInt

class GridListItemDecoration(private var spaceResId: Int) : RecyclerView.ItemDecoration() {

    var space = 0
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (space == 0) {
            space = view.resources.getDimension(spaceResId).roundToInt()
        }

        outRect.top = space
        outRect.bottom = space

        outRect.left = space
        outRect.right = space
    }
}