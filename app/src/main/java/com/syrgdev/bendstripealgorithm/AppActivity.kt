package com.syrgdev.bendstripealgorithm

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlin.math.max
import kotlin.math.min

const val TAG = "AppBend"

class AppActivity : Activity() {

    private val list = mutableListOf<Byte>()

    var currentX = 0
    var currentY = 0
    var maxX = 0
    var maxY = 0
    var minX = 0
    var minY = 0

    private val up: Byte = 0b00000001// 1
    private val down: Byte = 0b00000010//2
    private val left: Byte = 0b00000100//4
    private val right: Byte = 0b00001000//8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        run(n = 20)
    }

    private fun run(n: Int) {

        for (i in 1..n) {

            if (i == 1) {
                list.add(up)
                calculations(up)
                list.add(right)
                calculations(right)
                Log.d(TAG, "n: $i (${maxX - minX},${maxY - minY})")
                continue
            }

            for (y in (list.size - 1) downTo 0) {
                val direction = list[y].changeDirection()
                list.add(direction)
                calculations(direction)
            }

            Log.d(TAG, "n: $i (${maxX - minX},${maxY - minY})")
        }
    }

    private fun calculations(byte: Byte) {
        currentX = calculateX(byte)
        currentY = calculateY(byte)
        maxX = max(currentX, maxX)
        minX = min(currentX, minX)
        maxY = max(currentY, maxY)
        minY = min(currentY, minY)
    }

    private fun calculateX(byte: Byte): Int {
        return when (byte) {
            up -> currentX
            down -> currentX
            left -> currentX - 1
            right -> currentX + 1
            else -> throw RuntimeException()
        }
    }

    private fun calculateY(byte: Byte): Int {
        return when (byte) {
            up -> currentY - 1
            down -> currentY + 1
            left -> currentY
            right -> currentY
            else -> throw RuntimeException()
        }
    }

    private fun Byte.changeDirection(): Byte {
        return when (this) {
            up -> right
            down -> left
            left -> up
            right -> down
            else -> throw RuntimeException()
        }
    }
}