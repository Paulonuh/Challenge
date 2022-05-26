package com.paulo.myweatherchallenge.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.paulo.myweatherchallenge.R
import com.paulo.myweatherchallenge.model.animation.FragmentTransitionAnimation


/**
 * Created by Paulo Henrique Teixeira.
 */

fun Fragment.showToast(message: String) = context?.toastLong(message)

fun Fragment.showToast(messageId: Int) = context?.toastLong(messageId)


fun Fragment.navigate(
    directions: NavDirections
) {
    try {
        val navController = NavHostFragment.findNavController(this)
        val options = navOptions {
            anim {
                val fragmentAnimation = FragmentTransitionAnimation(
                    enter = R.anim.translate_left_enter,
                    exit = R.anim.translate_left_exit,
                    popEnter = R.anim.translate_right_enter,
                    popExit = R.anim.translate_right_exit
                )
                enter = fragmentAnimation.enter
                exit = fragmentAnimation.exit
                popEnter = fragmentAnimation.popEnter
                popExit = fragmentAnimation.popExit
            }
        }
        navController.navigate(directions.actionId, directions.arguments, options)
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}
