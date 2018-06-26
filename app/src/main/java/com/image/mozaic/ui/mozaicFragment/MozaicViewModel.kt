package com.image.mozaic.ui.mozaicFragment

import android.arch.lifecycle.ViewModel
import com.image.mozaic.genericRecyclerView.BaseRecyclerViewModel

class MozaicViewModel : ViewModel() {

    val mozaicItems = BaseRecyclerViewModel<VerticalItemViewHolder>(
            forceInitialLoad = true,
            loadHandler = { getNextMovieItemsInCollection() })

    private fun getNextMovieItemsInCollection() {
        mozaicItems.setIsLoading(true)
        val items = ArrayList<VerticalItemViewHolder>()
        items.add(VerticalItemViewHolder(listOf<ItemViewHolder>(ItemViewHolder("0.1"), ItemViewHolder("0.2"), ItemViewHolder("0.3"), ItemViewHolder("0.4"))))
        items.add(VerticalItemViewHolder(listOf<ItemViewHolder>(ItemViewHolder("1.1"), ItemViewHolder("1.2"), ItemViewHolder("1.3"), ItemViewHolder("1.4"))))
        items.add(VerticalItemViewHolder(listOf<ItemViewHolder>(ItemViewHolder("2.1"), ItemViewHolder("2.2"), ItemViewHolder("2.3"), ItemViewHolder("2.4"))))
        mozaicItems.setData(items)
    }
}