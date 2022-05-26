package com.paulo.myweatherchallenge.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.paulo.myweatherchallenge.R


/**
 * Created by Paulo Henrique Teixeira.
 */

fun isAtLeastPie(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}

fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

fun View?.increaseMarginTop(margin: Int) {
    this?.let {
        val layoutParams = it.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin + margin,
            layoutParams.rightMargin,
            layoutParams.bottomMargin
        )
        it.layoutParams = layoutParams
    }
}

fun AppCompatImageView?.loadImageCenter(
    @DrawableRes imageId: Int,
    @DrawableRes placeholderError: Int = R.color.gray_light,
    onLoadingFinished: (success: Boolean) -> Unit = {}
) {
    this?.let { imageView ->
        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished(false)
                return false
            }
        }

        Glide.with(imageView.context)
            .load(imageId)
            .apply(
                RequestOptions
                    .errorOf(placeholderError)
            )
            .listener(listener)
            .centerCrop()
            .into(imageView)
    }
}