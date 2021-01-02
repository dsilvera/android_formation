package com.dsilvera.myapplication

import kotlin.random.Random

interface Operation {
    fun result(): Int

    fun getOperationTemplate(): String

    fun getTextColor(): OperationTextColor

    companion object {
        fun generateOperation(level: Int): Operation {
            val num1 = Random.nextInt(0, 100)
            val num2 = Random.nextInt(0, 100)
            return if (level == 0) {
                Addition(num1, num2)
            } else if (level == 1) {
                if ((0..1).random() == 0) {
                    Addition(num1, num2)
                } else {
                    Substraction(num1, num2)
                }
            } else {
                when ((0..2).random()) {
                    0 -> Addition(num1, num2)
                    1 -> Substraction(num1, num2)
                    else -> Multiplication(num1, num2)
                }
            }
        }
    }
}

data class Addition(val num1: Int, val num2: Int) : Operation {
    override fun result() = num1 + num2
    override fun getOperationTemplate() = "$num1 + $num2"
    override fun getTextColor() = OperationTextColor.BLUE

    override fun toString(): String {
        return "$num1 + $num2 = ${result()}"
    }
}

data class Substraction(val num1: Int, val num2: Int) : Operation {
    override fun result() = num1 - num2

    override fun getOperationTemplate() = "$num1 - $num2"
    override fun getTextColor() = OperationTextColor.RED

    override fun toString(): String {
        return "$num1 - $num2 = ${result()}"
    }
}

data class Multiplication(val num1: Int, val num2: Int) : Operation {
    override fun result() = num1 * num2

    override fun getOperationTemplate() = "$num1 x $num2"
    override fun getTextColor() = OperationTextColor.GREEN

    override fun toString(): String {
        return "$num1 x $num2 = ${result()}"
    }
}

enum class OperationTextColor {
    BLUE, RED, GREEN
}