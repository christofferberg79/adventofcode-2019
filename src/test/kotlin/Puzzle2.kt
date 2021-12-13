package cberg.aoc2019

import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle2 {
    @Test
    fun testPart1() {
        assertEquals("2,0,0,0,99", runProgram("1,0,0,0,99"))
        assertEquals("2,3,0,6,99", runProgram("2,3,0,3,99"))
        assertEquals("2,4,4,5,99,9801", runProgram("2,4,4,5,99,0"))
        assertEquals("30,1,1,4,2,5,6,0,99", runProgram("1,1,1,4,99,5,6,0,99"))
        assertEquals(
            "3500,9,10,70,2,3,11,0,99,30,40,50",
            runProgram("1,9,10,3,2,3,11,0,99,30,40,50")
        )

        val input = readInput("input2.txt")
        val result = part1(input, 12L, 2L)
        assertEquals(3716250, result)
    }

    @Test
    fun testPart2() {
        val input = readInput("input2.txt")
        val result = part2(input)
        assertEquals(6472, result)
    }
}

private fun runProgram(input: String) = Intcode(input).apply { run() }.toString()

private fun part1(input: String, noun: Long, verb: Long): Long {
    val ic = Intcode(input)
    ic[1] = noun
    ic[2] = verb
    ic.run()
    return ic[0]
}

private fun part2(input: String): Long {
    for (noun in 0L..99) {
        for (verb in 0L..99) {
            if (part1(input, noun, verb) == 19690720L) {
                return 100 * noun + verb
            }
        }
    }
    error("No solution found")
}
