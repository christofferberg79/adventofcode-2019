package cberg.aoc2019

import cberg.aoc2019.common.AsciiComputer
import cberg.aoc2019.common.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle21 {
    @Test
    fun testPart1() {
        val input = readInput("input21.txt")
        assertEquals(19361850, part1(input))
    }

    @Test
    fun testPart2() {
        val input = readInput("input21.txt")
        assertEquals(1138943788, part2(input))
    }
}

private fun part1(program: String) = AsciiComputer(program).run {
    // J := NOT (A AND B AND C) AND D
    sendInput(
        """
        OR A J
        AND B J
        AND C J
        NOT J J
        AND D J
        WALK
        """.trimIndent()
    )

    result
}

private fun part2(program: String) = AsciiComputer(program).run {

    // J := NOT (A AND B AND C) AND D AND H OR NOT A
    sendInput(
        """
        OR A J
        AND B J
        AND C J
        NOT J J
        AND D J
        AND H J
        NOT A T
        OR T J
        RUN
        """.trimIndent()
    )

    result
}
