package cberg.aoc2019

import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import kotlin.test.Test
import kotlin.math.sign
import kotlin.test.assertEquals

class Puzzle13 {
    @Test
    fun testPart1() {
        val input = readInput("input13.txt")
        val result = part1(input)
        assertEquals(398, result)
    }

    @Test
    fun testPart2() {
        val input = readInput("input13.txt")
        val result = part2(input)
        assertEquals(19447, result)
    }
}

private fun part1(input: String): Int {
    val ic = Intcode(input)
    ic.run()
    return ic.receiveOutput().chunked(3).count { it.last() == 2L }
}

private fun part2(input: String): Long {
    val ic = Intcode(input)
    ic[0] = 2
    var score = 0L
    var ballX = 0L
    var paddleX = 0L
    while (ic.isRunning) {
        ic.run()
        ic.receiveOutput().chunked(3).forEach { (x, y, z) ->
            when {
                x == -1L && y == 0L -> score = z
                z == 3L -> paddleX = x
                z == 4L -> ballX = x
            }
        }
        ic.sendInput((ballX - paddleX).sign.toLong())
    }
    return score
}
