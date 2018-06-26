package com.image.mozaic.genericRecyclerView

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Default Adapter should be used for most recyclerviews lists
 *
 */
class GenericRecyclerViewAdapter<T : BaseRecyclerViewItem>(private val loadingItem: LoadingItem? = LoadingItem()) : RecyclerView.Adapter<BaseViewHolder>() {

    private val data = mutableListOf<T>()
    private var isLoading: Boolean = false

    var clickListener: ((T) -> Unit)? = null

    @Suppress("UNCHECKED_CAST")
    private val LOADING_ITEM = loadingItem as T

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), viewType, parent, false)
        val viewHolder = BaseViewHolder(binding)

        viewHolder.itemView.isFocusableInTouchMode = false
        viewHolder.itemView.isFocusable = false
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION && position < data.size) {
                val item = data[position]
                if (!item.shouldIgnoreClickListener()) {
                    clickListener?.invoke(item)
                }
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val obj = data[position]
        holder.bind(obj)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int) = data[position].provideLayout()

    fun prepareToLoadNextPage() {
        if (!isLoading) {
            isLoading = true
            data.add(LOADING_ITEM)
            this.notifyItemInserted(data.size - 1)
        }
    }

    fun loadingNextPageFinished() {
        if (isLoading) {
            isLoading = false
            val lastIndex = data.indexOf(LOADING_ITEM)
            data.remove(LOADING_ITEM)
            this.notifyItemRemoved(lastIndex)
        }
    }

    fun setItems(items: List<T>) {
        loadingNextPageFinished()
        val diffCallback = DiffCallback(data, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getData(): List<T> = data

    /**
     * TODO be careful when using this method
     */
    fun clearData() {
        data.clear()
        this.notifyDataSetChanged()
    }

    private class DiffCallback(
            private val oldList: List<BaseRecyclerViewItem>,
            private val newList: List<BaseRecyclerViewItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}
