package com.image.mozaic.genericRecyclerView


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (allElementsAreVisibleOrLastElementReached(recyclerView)) {
            this.onScrolledToBottom(recyclerView)
        }
    }

    private fun allElementsAreVisibleOrLastElementReached(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            var completelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
            if (completelyVisibleItemPosition == -1) {
                completelyVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }
            val itemCount = layoutManager.itemCount
            return itemCount - completelyVisibleItemPosition <= 1
        } else {
            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
            val lastVisible = IntArray(4)
            layoutManager.findLastCompletelyVisibleItemPositions(lastVisible)
            val itemCount = layoutManager.itemCount
            //TODO
            return itemCount - lastVisible[0] <= 2
        }
    }

    abstract fun onScrolledToBottom(recyclerView: RecyclerView?)
}
