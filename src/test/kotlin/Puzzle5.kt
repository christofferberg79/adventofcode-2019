package cberg.aoc2019

import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Puzzle5 {
    @Test
    fun testPart1() {
        val output = run(1L, readInput("input5.txt"))
        assertTrue(output.dropLast(1).all { it == 0L })
        assertEquals(13933662, output.last())
    }

    @Test
    fun testPart2() {
        val output = run(5L, readInput("input5.txt"))
        assertTrue(output.dropLast(1).all { it == 0L })
        assertEquals(2369720, output.last())
    }
}

private fun run(input: Long, program: String): List<Long> {
    val computer = Intcode(program)
    computer.sendInput(input)
    computer.run()
    return computer.receiveOutput().toList()
}
