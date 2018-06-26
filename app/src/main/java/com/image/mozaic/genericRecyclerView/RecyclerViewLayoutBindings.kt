package com.image.mozaic.genericRecyclerView

import android.databinding.BindingAdapter
import android.support.annotation.ColorRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import com.image.mozaic.utils.GridListItemDecoration
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration


@Suppress("unused")
object RecyclerViewLayoutBindings {

    @JvmStatic
    @BindingAdapter(value = ["adapter_data", "item_click_listener"], requireAll = false)
    fun <T : BaseRecyclerViewItem> setRecyclerViewAdapter(view: RecyclerView,
                                                             data: List<T>?,
                                                             clickHandler: ((T) -> Unit)?) {

        if (view.adapter == null) {

            val adapter: GenericRecyclerViewAdapter<T> = GenericRecyclerViewAdapter()

            adapter.clickListener = clickHandler

            view.adapter = adapter
        }

        if (data != null) {
            (view.adapter as? GenericRecyclerViewAdapter<T>)?.setItems(data)
        } else {
            (view.adapter as? GenericRecyclerViewAdapter<T>)?.loadingNextPageFinished()
        }

        val layoutManager = view.layoutManager
        if (layoutManager is StaggeredGridLayoutManager) {
            layoutManager.invalidateSpanAssignments()
        }

        view.itemAnimator?.let {
            (it as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
    }

    @JvmStatic
    @BindingAdapter("items_loader")
    fun setItemsListener(view: RecyclerView, load: () -> Unit?) {
        view.addOnScrollListener(object : EndlessRecyclerViewScrollListener() {
            override fun onScrolledToBottom(recyclerView: RecyclerView?) {
                load()
            }
        })
    }

    @JvmStatic
    @BindingAdapter("isLoading")
    fun setRecyclerViewIsLoading(view: RecyclerView, isLoading: Boolean) {
        if (isLoading) {
            (view.adapter as? GenericRecyclerViewAdapter<*>)?.prepareToLoadNextPage()
        } else {
            (view.adapter as? GenericRecyclerViewAdapter<*>)?.loadingNextPageFinished()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["item_decoration_color", "item_decoration_size"], requireAll = true)
    fun setRecyclerViewItemDecoration(view: RecyclerView, @ColorRes colorResId: Int, sizeResId: Int) {
        if (colorResId != 0 && sizeResId != 0) {

            val layoutManager = view.layoutManager

            if (layoutManager is StaggeredGridLayoutManager) {
                view.addItemDecoration(GridListItemDecoration(sizeResId))
            } else if (layoutManager is LinearLayoutManager && layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                view.addItemDecoration(HorizontalDividerItemDecoration.Builder(view.context)
                        .colorResId(colorResId)
                        .sizeResId(sizeResId)
                        .showLastDivider()
                        .build())
            } else {
                view.addItemDecoration(
                        VerticalDividerItemDecoration.Builder(view.context)
                                .colorResId(colorResId)
                                .sizeResId(sizeResId)
                                .showLastDivider()
                                .build())
            }

        }
    }

}