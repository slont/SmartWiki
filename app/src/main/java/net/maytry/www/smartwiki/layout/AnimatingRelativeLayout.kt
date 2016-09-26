package net.maytry.www.smartwiki.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import net.maytry.www.smartwiki.R

/**
 * Created by slont on 9/22/16.
 */

class AnimatingRelativeLayout(internal val context: Context, attrs: AttributeSet?, defStyle: Int) : RelativeLayout(context, attrs, defStyle) {
    internal var inAnimation: Animation
    internal var outAnimation: Animation

    init {
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom) as Animation
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom) as Animation
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    fun show() {
        if (isVisible) return
        show(true)
    }

    fun show(withAnimation: Boolean) {
        if (withAnimation) this.startAnimation(inAnimation)
        this.visibility = View.VISIBLE
    }

    fun hide() {
        if (!isVisible) return
        hide(true)
    }

    fun hide(withAnimation: Boolean) {
        if (withAnimation) this.startAnimation(outAnimation)
        this.visibility = View.GONE
    }

    val isVisible: Boolean
        get() = this.visibility == View.VISIBLE
}
