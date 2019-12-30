package cberg.aoc2019

import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Puzzle5 {
    @Test
    fun part1() {
        val output = run(1L)
        assertTrue(output.dropLast(1).all { it == 0L })
        assertEquals(13933662, output.last())
    }

    @Test
    fun part2() {
        val output = run(5L)
        assertTrue(output.dropLast(1).all { it == 0L })
        assertEquals(2369720, output.last())
    }
}

private fun run(input: Long): List<Long> {
    val computer = Intcode(readInput("input5.txt"))
    computer.sendInput(input)
    computer.run()
    return computer.receiveAllOutput().toList()
}
