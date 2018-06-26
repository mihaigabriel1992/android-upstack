package com.image.mozaic.ui.mozaicFragment

import com.image.mozaic.R
import com.image.mozaic.genericRecyclerView.BaseRecyclerViewItem
import java.util.*

class ItemViewHolder(val title: String = ""): BaseRecyclerViewItem {

    override val id: String = UUID.randomUUID().toString()

    override fun provideLayout() = R.layout.vh_item
}