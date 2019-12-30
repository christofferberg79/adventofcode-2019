package cberg.aoc2019.puzzle1

import org.junit.Test
import cberg.aoc2019.readInputLines
import kotlin.test.assertEquals

class Puzzle1 {
    @Test
    fun part1() {
        assertEquals(2, listOf(12).sumFuel())
        assertEquals(2, listOf(14).sumFuel())
        assertEquals(654, listOf(1969).sumFuel())
        assertEquals(33583, listOf(100756).sumFuel())
        assertEquals(3263320, getMassesFromInputFile().sumFuel())
    }

    @Test
    fun part2() {
        assertEquals(2, listOf(14).sumFuel2())
        assertEquals(966, listOf(1969).sumFuel2())
        assertEquals(50346, listOf(100756).sumFuel2())
        assertEquals(4892135, getMassesFromInputFile().sumFuel2())
    }
}

private fun List<Int>.sumFuel() = sumBy { mass -> mass / 3 - 2 }

private fun List<Int>.sumFuel2() = sumBy { mass ->
    generateSequence(mass) { additionalMass -> additionalMass / 3 - 2 }
        .drop(1)
        .takeWhile { fuel -> fuel > 0 }
        .sum()
}

private fun getMassesFromInputFile(): List<Int> = readInputLines("input1.txt").map { it.toInt() }
