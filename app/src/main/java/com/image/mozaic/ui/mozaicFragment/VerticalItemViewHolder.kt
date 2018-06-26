package com.image.mozaic.ui.mozaicFragment

import com.image.mozaic.R
import com.image.mozaic.genericRecyclerView.BaseRecyclerViewItem
import com.image.mozaic.genericRecyclerView.BaseRecyclerViewModel
import java.util.*

class VerticalItemViewHolder(val items: List<ItemViewHolder>): BaseRecyclerViewItem {

    val horizontalItems = BaseRecyclerViewModel<ItemViewHolder>(
            forceInitialLoad = true,
            loadHandler = { getNextMovieItemsInCollection() })

    private fun getNextMovieItemsInCollection() {
        horizontalItems.setIsLoading(true)
        horizontalItems.setData(items)
    }

    override val id: String = UUID.randomUUID().toString()

    override fun provideLayout() = R.layout.vh_vertical_item
}