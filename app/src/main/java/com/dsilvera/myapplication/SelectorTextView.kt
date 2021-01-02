package com.dsilvera.myapplication

import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.core.content.ContextCompat

class SelectorTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        ellipsize = TextUtils.TruncateAt.END
        maxLines = 1
        gravity = Gravity.CENTER_HORIZONTAL
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        isAllCaps = true
        setTextColor(ContextCompat.getColor(context, R.color.grey_afb4c8))
        val padding = (12 * Resources.getSystem().displayMetrics.density).toInt()
        setPadding(0, padding, 0, padding)
    }

}