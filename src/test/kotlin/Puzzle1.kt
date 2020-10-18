package cberg.aoc2019

import cberg.aoc2019.common.readInputLines
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle1 {
    @Test
    fun testPart1() {
        assertEquals(2, part1(listOf(12)))
        assertEquals(2, part1(listOf(14)))
        assertEquals(654, part1(listOf(1969)))
        assertEquals(33583, part1(listOf(100756)))
        assertEquals(3263320, part1(getMassesFromInputFile()))
    }

    @Test
    fun testPart2() {
        assertEquals(2, part2(listOf(14)))
        assertEquals(966, part2(listOf(1969)))
        assertEquals(50346, part2(listOf(100756)))
        assertEquals(4892135, part2(getMassesFromInputFile()))
    }
}

private fun part1(input: List<Int>) = input.sumBy { mass -> mass / 3 - 2 }

private fun part2(input: List<Int>) = input.sumBy { mass ->
    generateSequence(mass) { additionalMass -> additionalMass / 3 - 2 }
        .drop(1)
        .takeWhile { fuel -> fuel > 0 }
        .sum()
}

private fun getMassesFromInputFile(): List<Int> = readInputLines("input1.txt").map { it.toInt() }
