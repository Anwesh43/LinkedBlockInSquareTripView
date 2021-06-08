package com.example.blockinsquaretripview

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#673AB7",
    "#00C853",
    "#304FFE",
    "#BF360C"
).map {
    Color.parseColor(it)
}.toTypedArray()
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
val parts : Int = 5
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 4.9f
val blockSizeFactor : Float = 15.9f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n

fun Canvas.drawBlockInSquareTrip(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sc1 : Float = scale.divideScale(0, parts)
    val sc2 : Float = scale.divideScale(1, parts)
    val sc3 : Float = scale.divideScale(2, parts)
    val sc4 : Float = scale.divideScale(3, parts)
    val sc5 : Float = scale.divideScale(4, parts)
    val blockSize : Float = Math.min(w, h) / blockSizeFactor
    val bc : Float = blockSize * sc2
    save()
    translate(w / 2, h / 2)
    save()
    translate((w / 2 + size) * sc5, -h / 2 - size / 2 + (h / 2 + size / 2) * sc1)
    paint.style = Paint.Style.STROKE
    drawRect(RectF(-size / 2, -size / 2, size / 2, size / 2), paint)
    restore()
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f - 2 * j)
        for (k in 0..1) {
            save()
            translate(size / 2 - size * sc3 * (1 - k), (size / 2) - size * sc3 * k)
            drawRect(RectF(-bc / 2, -bc / 2, bc / 2, bc / 2), paint)
            restore()
        }
        restore()
    }
    restore()
}

fun Canvas.drawBISTNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawBlockInSquareTrip(scale, w, h, paint)
}

