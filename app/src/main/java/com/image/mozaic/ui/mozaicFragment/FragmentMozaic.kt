package com.image.mozaic.ui.mozaicFragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.image.mozaic.R
import com.image.mozaic.databinding.FragmentMozaicBinding
import com.image.mozaic.BR
import com.image.mozaic.genericRecyclerView.BaseRecyclerViewModel

class FragmentMozaic : Fragment() {

    companion object {
        val TAG: String = FragmentMozaic::class.java.simpleName
    }


    protected lateinit var viewModel: MozaicViewModel

    protected lateinit var binding: FragmentMozaicBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MozaicViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onBindView(inflater, container)
        return binding.root
    }

    private fun onBindView(inflater: LayoutInflater, container: ViewGroup?): FragmentMozaicBinding {
        val binding = DataBindingUtil.inflate<FragmentMozaicBinding>(inflater, R.layout.fragment_mozaic, container, false)
        binding.setLifecycleOwner(this)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mozaicItems.collectionId = BaseRecyclerViewModel.DUMMY_COLLECTION_ID
    }

}


