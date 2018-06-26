package com.image.mozaic.genericRecyclerView

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.image.mozaic.BR

class BaseViewHolder(private val binding: ViewDataBinding?) : RecyclerView.ViewHolder(binding?.root!!) {

    fun bind(item: Any?) {
        binding?.setVariable(BR.obj, item)
        binding?.executePendingBindings()
    }
}