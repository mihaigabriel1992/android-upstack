package com.image.mozaic.genericRecyclerView

import android.support.annotation.LayoutRes

interface BaseRecyclerViewItem {

    val id: String?

    @LayoutRes
    fun provideLayout(): Int

    fun shouldIgnoreClickListener(): Boolean = false
}