package com.image.mozaic.genericRecyclerView

import com.image.mozaic.R


open class LoadingItem : BaseRecyclerViewItem {

    companion object {
        private val ID = LoadingItem::class.java.simpleName
    }

    override val id: String? = ID

    override fun provideLayout() = R.layout.loading_item

    override fun shouldIgnoreClickListener() = true

}