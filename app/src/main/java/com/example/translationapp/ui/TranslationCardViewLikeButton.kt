package com.example.translationapp.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.animation.ScaleAnimation
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.translationapp.R

class TranslationCardViewLikeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var isLiked = false

    @DrawableRes
    private var likedResId: Int = R.drawable.ic_star_filled
    @DrawableRes
    private var unlikedResId: Int = R.drawable.ic_star_outline

    private var likedDrawable: Drawable? = null
    private var unlikedDrawable: Drawable? = null

    init {
        if (attrs != null) {
            val styleAttr = context.obtainStyledAttributes(attrs, R.styleable.LikeButton)
            likedResId = styleAttr.getResourceId(
                R.styleable.LikeButton_likedSrc,
                likedResId
            )
            unlikedResId = styleAttr.getResourceId(
                R.styleable.LikeButton_unlikedSrc,
                unlikedResId
            )
            isLiked = styleAttr.getBoolean(R.styleable.LikeButton_isLiked, false)
            styleAttr.recycle()
        }

        likedDrawable = ContextCompat.getDrawable(context, likedResId)
        unlikedDrawable = ContextCompat.getDrawable(context, unlikedResId)

        setImageDrawable(unlikedDrawable)

        setOnClickListener {
            toggleLike()
        }
    }

    private fun toggleLike() {
        isLiked = !isLiked
        setImageDrawable(if (isLiked) likedDrawable else unlikedDrawable)
        animateButton()
    }

    private fun animateButton() {
        val scaleUp = ScaleAnimation(
            0.7f, 1.0f, 0.7f, 1.0f,
            (this.width / 2).toFloat(),
            (this.height / 2).toFloat()
        ).apply {
            duration = 200
            fillAfter = true
        }
        startAnimation(scaleUp)
    }

    fun setLiked(liked: Boolean) {
        isLiked = liked
        setImageDrawable(if (isLiked) likedDrawable else unlikedDrawable)
    }
}