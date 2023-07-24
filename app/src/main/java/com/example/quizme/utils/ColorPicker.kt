package com.example.quizme.utils

object ColorPicker {
    private var colors= arrayOf("#FF99FF", "#3EB90F", "#3685BC", "#D36280", "#E44F55", "#FA8056","#D4AF37", "#5D6D7E", "#7D3C98", "#8E44AD", "#5499C7")
    var currentColorIndex = 0

    fun getColor():String{
        currentColorIndex = (currentColorIndex+1)% colors.size
        return colors[currentColorIndex]
    }
}