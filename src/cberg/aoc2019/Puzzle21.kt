package cberg.aoc2019

import cberg.aoc2019.common.AsciiComputer
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle21 {
    @Test
    fun testPart1() {
        val input = readInput("input21.txt")
        assertEquals(19361850, part1(input))
    }
}

private fun part1(program: String): Long {
    val ac = AsciiComputer(program)

    // J := (NOT A OR NOT B OR NOT C) AND D
    ac.sendInput("""
        NOT A J
        NOT B T
        OR T J
        NOT C T
        OR T J
        AND D J
        WALK
    """.trimIndent())

    return ac.result
}
