package com.paulo.myweatherchallenge.widget

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView
import com.paulo.myweatherchallenge.R


/**
 * Created by Paulo Henrique Teixeira.
 */


class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val titleTextView: MaterialTextView

    var title: String? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ToolbarView, defStyleAttr, 0)
            .apply {
                try {
                    val title = getString(R.styleable.ToolbarView_toolbarTitle)
                    val titleColor = getColor(
                        R.styleable.ToolbarView_toolbarTitleColor,
                        Color.WHITE
                    )
                    titleTextView = createTitleTextView(title, titleColor)

                    setConstraints()
                } finally {
                    recycle()
                }
            }
    }

    private fun createTitleTextView(title: String?, titleColor: Int): MaterialTextView {
        return MaterialTextView(context).apply {
            id = R.id.centerTextView
            text = title
            setTextColor(titleColor)
            isVisible = !title.isNullOrEmpty()
            textSize = 20f
            maxLines = 1
            isSingleLine = true
            gravity = Gravity.CENTER
            ellipsize = TextUtils.TruncateAt.END

            addView(
                this,
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun convertToDimensionValue(value: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            metrics
        )
    }

    private fun setConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        //region Title Text
        val margin = convertToDimensionValue(16f)

        constraintSet.connect(
            titleTextView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            margin.toInt()
        )
        constraintSet.connect(
            titleTextView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            35
        )
        constraintSet.connect(
            titleTextView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(
            titleTextView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
        //endregion Center Text

        constraintSet.applyTo(this)
    }

    fun updateTitle(@StringRes idRes: Int) {
        titleTextView.setText(idRes)
    }

    fun updateTitle(name: String) {
        titleTextView.text = name
    }

    fun setTitleVisibility(isVisible: Boolean) {
        titleTextView.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun getCurTitle(): CharSequence? {
        return titleTextView.text
    }

}