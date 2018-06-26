package com.image.mozaic.genericRecyclerView

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.image.mozaic.BR

/**
 * This class describes an RecyclerView that uses the EndlessRecyclerAdapter,
 * and provides the data, loading mechanism and a click.
 *
 * When endless behaviour is require you should use this class (or subclasses)
 * as a data binding variable
 *
 * @see [EndlessLayoutBidings] for data bindings
 */
open class BaseRecyclerViewModel<T : BaseRecyclerViewItem>(
        loadHandler: ((BaseRecyclerViewModel<T>) -> Unit)? = null,
        private var forceInitialLoad: Boolean = false,
        val clickHandler: ((T) -> Unit)? = null)
    : BaseObservable() {

    companion object {
        const val DUMMY_COLLECTION_ID = "dummy_collection_id" // to be used for collections that do not require an id to be fetched (eg. search based collections)
    }

    var collectionId: String? = null
        set(value) {
            val original = field
            field = value
            if (original == null && value != null && forceInitialLoad) {
                forceInitialLoad = false
                loader()
            }
        }

    private var smoothScrollToPosition = 0

    private var data = mutableListOf<T>()

    @Bindable
    fun getData() = data

//    private val paginationMapping = mutableMapOf<PageInfo?, Long>()
//    var paginationInfo: PageInfo? = null
//        private set

    fun removeItem(id: String?) {
        id?.let {
            getItemById(id)?.let {
                data.remove(it)
                notifyPropertyChanged(BR.data)
            }
        }
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyPropertyChanged(BR.data)
    }

    private fun getItemById(id: String?): T? {
        if (data.isNotEmpty()) {
            data
                    .filter { it.id == id }
                    .forEach { return it }
        }
        return null
    }


    /**
     * If pageInfo is not in paginationMapping then just add the items,
     * else update the list.
     * Returns true if the action was an insertion and not an updated.
     */
    open fun setData(newData: List<T>
//                     , pageInfo: PageInfo?
    ) {

//        val mappedIndex = paginationMapping[pageInfo]
//        if (mappedIndex == null) {
//            paginationMapping[pageInfo] = this.paginationInfo?.count ?: 0
//            this.paginationInfo = pageInfo
//        }

//        if (mappedIndex == null) {
            data.addAll(newData.toMutableList())
//        } else {
//            val pre = if (0 < mappedIndex.toInt()) data.subList(0, mappedIndex.toInt()) else null
//            val postStartIndex = mappedIndex.toInt() + newData.size
//            val post = if (postStartIndex < data.size) data.subList(postStartIndex, data.size) else null
//            val data = mutableListOf<T>()
//            data.apply {
//                if (pre != null) {
//                    addAll(pre)
//                }
//                addAll(newData)
//                if (post != null) {
//                    addAll(post)
//                }
//            }
//
//            this.data = data
//        }
        notifyPropertyChanged(BR.data)

//        return mappedIndex == null
    }

    val loader = {
        val collectionId = collectionId
        if (!isLoading && collectionId != null && data.size == 0) {
                loadHandler?.invoke(this)
        }
    }

    private var isLoading = false

    @Bindable
    fun getIsLoading() = isLoading

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyPropertyChanged(BR.isLoading)
    }

    fun clear() {
//        paginationMapping.clear()
//        paginationInfo = null
        data.clear()
        notifyPropertyChanged(BR.data)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseRecyclerViewModel<*>

        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }


}
