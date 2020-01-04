package cberg.aoc2019

import cberg.aoc2019.common.*
import cberg.aoc2019.common.Coordinate.Companion.down
import cberg.aoc2019.common.Coordinate.Companion.left
import cberg.aoc2019.common.Coordinate.Companion.right
import cberg.aoc2019.common.Coordinate.Companion.up
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

private fun part2(input: String): Long {
    val program = input.let { "2" + it.substring(1) }
    val ac = AsciiComputer(program)
    ac.sendInput("""
        A,B,A,C,B,C,B,A,C,B
        L,10,L,6,R,10
        R,6,R,8,R,8,L,6,R,8
        L,10,R,8,R,8,L,10
        n
    """.trimIndent())
    return ac.result
}
