package com.paulo.myweatherchallenge.base

interface BaseFragmentContract {

    fun onInitViews()
    fun onInitObservers()
    fun onFetchInitialData()

    fun showError(message: Int)
    fun onLoading(isLoading: Boolean)

}