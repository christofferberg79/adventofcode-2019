package cberg.aoc2019.puzzle2

import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle2 {
    @Test
    fun part1() {
        assertEquals("2,0,0,0,99", runProgram("1,0,0,0,99"))
        assertEquals("2,3,0,6,99", runProgram("2,3,0,3,99"))
        assertEquals("2,4,4,5,99,9801", runProgram("2,4,4,5,99,0"))
        assertEquals("30,1,1,4,2,5,6,0,99", runProgram("1,1,1,4,99,5,6,0,99"))
        assertEquals("3500,9,10,70,2,3,11,0,99,30,40,50", runProgram("1,9,10,3,2,3,11,0,99,30,40,50"))

        val input = readInput("input2.txt")
        val result = execute(input, 12L, 2L)[0]
        assertEquals(3716250, result)
    }

    @Test
    fun part2() {
        val (noun, verb) = getNounAndVerb(readInput("input2.txt"), 19690720)
        assertEquals(6472, 100 * noun + verb)
    }
}

private fun runProgram(input: String) = execute(input).toList().joinToString(separator = ",")

private fun execute(input: String, noun: Long? = null, verb: Long? = null): Intcode {
    val ic = Intcode(input)
    if (noun != null) ic[1] = noun
    if (verb != null) ic[2] = verb
    ic.run()
    return ic
}

private fun getNounAndVerb(input: String, target: Long): Pair<Long, Long> {
    for (noun in 0L..99) {
        for (verb in 0L..99) {
            if (execute(input, noun, verb)[0] == target) {
                return Pair(noun, verb)
            }
        }
    }
    error("No solution found")
}

