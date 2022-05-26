package com.paulo.myweatherchallenge.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * Created by Paulo Henrique Teixeira.
 */

abstract class BaseFragment<VB : ViewBinding> : Fragment(),
    BaseFragmentContract {


    private var mViewBinding: VB? = null

    abstract val viewModel: BaseViewModel?
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = mViewBinding as VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.messaging?.observe(viewLifecycleOwner) {
            showError(it)
        }

        viewModel?.loading?.observe(viewLifecycleOwner) { isLoading ->
            onLoading(isLoading)
        }

        onFetchInitialData()
        onInitViews()
        onInitObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewBinding = null
    }
}