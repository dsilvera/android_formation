package com.dsilvera.myapplication

import android.content.Context
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat

class SelectorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onValueChanged: ((Int) -> Unit)? = null
    private val textViews = ArrayList<TextView>()

    @StringRes
    private var valueSelected: Int? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_selector, this, true)

        setBackgroundResource(R.drawable.background_grey_f7f7f7_radius_10)
    }

    fun getValue() = valueSelected

    private fun updateSelectorBackground(idSelected: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(R.id.viewSelectorBackgroundSelected, ConstraintSet.START, idSelected, ConstraintSet.START)

        val transition = ChangeBounds()
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
            }

            override fun onTransitionEnd(transition: Transition) {
                updateTextColors(idSelected)
            }

            override fun onTransitionCancel(transition: Transition) {
            }

            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionResume(transition: Transition) {
            }
        })

        TransitionManager.beginDelayedTransition(this, transition)
        constraintSet.applyTo(this)
    }

    private fun updateTextColors(idSelected: Int) {
        textViews.forEach {
            it.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (idSelected == it.id) R.color.yellow_fefc04 else R.color.grey_afb4c8
                )
            )
        }
    }

    fun init(@StringRes values: ArrayList<Int>) {
        if (values.size <= 1) throw Exception("You need at least 2 values")
        addTextViews(values)
        refreshTextViewsConstraintSet(values)
        setDefaultValue()
    }

    private fun setDefaultValue() {
        if (textViews.size > 0) {
            val idSelected = textViews[0].id
            updateSelectorBackground(idSelected)
            updateTextColors(idSelected)
        }
    }

    private fun addTextViews(@StringRes values: ArrayList<Int>) {
        textViews.clear()
        removeViews(1, childCount - 1)
        values.forEachIndexed { index, value ->
            if (index == 0) valueSelected = value
            val textView = SelectorTextView(context)
            val id = "selector_textview_$index".hashCode()
            textView.id = id
            textView.layoutParams = LayoutParams(0, WRAP_CONTENT)
            textView.setText(value)

            textView.setOnClickListener {
                updateSelectorBackground(id)
                onValueChanged?.invoke(value)
                valueSelected = value
            }
            textViews.add(textView)
            addView(textView)
        }
    }

    private fun refreshTextViewsConstraintSet(@StringRes values: ArrayList<Int>) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        val percent = 1f / values.size
        var previousId: Int? = null
        textViews.forEach { textView ->
            constraintSet.constrainPercentWidth(textView.id, percent)
            constraintSet.connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

            if (previousId == null) {
                constraintSet.connect(textView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            } else {
                previousId?.let { previousId ->
                    constraintSet.connect(textView.id, ConstraintSet.START, previousId, ConstraintSet.END)
                }
            }

            previousId = textView.id
        }

        constraintSet.constrainPercentWidth(R.id.viewSelectorBackgroundSelected, percent)
        constraintSet.applyTo(this)
    }
}
