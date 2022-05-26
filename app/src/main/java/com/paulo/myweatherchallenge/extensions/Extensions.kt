package com.paulo.myweatherchallenge.extensions

import android.content.Context
import android.widget.Toast


/**
 * Created by Paulo Henrique Teixeira.
 */

fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
