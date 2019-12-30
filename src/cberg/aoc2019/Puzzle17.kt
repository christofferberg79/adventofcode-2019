package cberg.aoc2019

import cberg.aoc2019.common.Coordinate
import cberg.aoc2019.common.Coordinate.Companion.down
import cberg.aoc2019.common.Coordinate.Companion.left
import cberg.aoc2019.common.Coordinate.Companion.right
import cberg.aoc2019.common.Coordinate.Companion.up
import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.plus
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle17 {
    @Test
    fun testPart1() {
        val input = readInput("input17.txt")
        assertEquals(9876, part1(input))
    }

    @Test
    fun testPart2() {
        val input = readInput("input17.txt")
        assertEquals(1234055, part2(input))
    }
}

private fun part1(program: String): Int {
    val ic = Intcode(program)
    var x = 0
    var y = 0
    val map = mutableMapOf<Coordinate, Char>()

    ic.run()
    ic.receiveOutput().forEach { output ->
        if (output == 10L) {
            x = 0
            y++
        } else {
            map[Coordinate(x, y)] = output.toChar()
            x++
        }
    }

    return map.keys.filter { listOf(it, it + up, it + down, it + right, it + left).all { map[it] == '#' } }
        .map { (x, y) -> x * y }.sum()
}

private fun part2(program: String): Int {
    val input = "A,B,A,C,B,C,B,A,C,B\n" +
            "L,10,L,6,R,10\n" +
            "R,6,R,8,R,8,L,6,R,8\n" +
            "L,10,R,8,R,8,L,10\n" +
            "n\n"

    val ic = Intcode(program)
    ic[0] = 2

    input.forEach {
        ic.sendInput(it.toLong())
    }

    ic.run()

    return ic.receiveOutput().last().toInt()
}
