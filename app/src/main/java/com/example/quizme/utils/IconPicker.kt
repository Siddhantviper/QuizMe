package com.example.quizme.utils

import com.example.quizme.R

object IconPicker {
    private val icons = arrayOf(
        R.drawable.ic_1,
        R.drawable.ic_2,
        R.drawable.ic_3,
        R.drawable.ic_4,
        R.drawable.ic_5,
        R.drawable.ic_6,


        R.drawable.ic_11,
        R.drawable.ic_12,
        R.drawable.ic_13,
        R.drawable.ic_14,
        R.drawable.ic_15,


    )
    var currentIconIndex = 0

    fun getIcon():Int{
        currentIconIndex = (currentIconIndex+1)% icons.size
        return icons[currentIconIndex]
    }

}