package com.example.walker.myhencoder.demo.gift.view

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.TypedValue

fun dp2px(context: Context, dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()

fun getScreenWidth(context: Activity): Int {
    val defaultDisplay = context.windowManager.defaultDisplay
    val point = Point()
    defaultDisplay.getSize(point)
    return point.x
}

fun getScreenHeight(context: Activity): Int {
    val defaultDisplay = context.windowManager.defaultDisplay
    val point = Point()
    defaultDisplay.getSize(point)
    return point.y
}