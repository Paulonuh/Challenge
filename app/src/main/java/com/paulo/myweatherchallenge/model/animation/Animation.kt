package com.paulo.myweatherchallenge.model.animation

import androidx.annotation.AnimRes

data class FragmentTransitionAnimation(
    @AnimRes val enter: Int,
    @AnimRes val exit: Int,
    @AnimRes val popEnter: Int,
    @AnimRes val popExit: Int
)
