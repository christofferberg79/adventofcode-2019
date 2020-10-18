package cberg.aoc2019.common

import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val up = Coordinate(0, -1)
        val left = Coordinate(-1, 0)
        val down = Coordinate(0, 1)
        val right = Coordinate(1, 0)
    }
}

operator fun Coordinate.plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
operator fun Coordinate.minus(other: Coordinate) = Coordinate(x - other.x, y - other.y)
operator fun Coordinate.unaryMinus() = Coordinate(-x, -y)

val Coordinate.manhattanDistance get() = abs(x) + abs(y)
val Coordinate.quadrant
    get() = when {
        x >= 0 && y < 0 -> 1
        x > 0 && y >= 0 -> 2
        x <= 0 && y > 0 -> 3
        x < 0 && y <= 0 -> 4
        else -> error("$this is in no quadrant")
    }

fun Coordinate.turnLeft() = Coordinate(y, -x)
fun Coordinate.turnRight() = Coordinate(-y, x)
