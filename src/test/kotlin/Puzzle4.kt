package cberg.aoc2019

import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle4 {
    private val input = 245182..790572

    @Test
    fun testPart1() {
        assertEquals(1099, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(710, part2(input))
    }
}

private fun part1(input: IntRange) = input.count { value ->
    with(value.digits) { isSorted() && groupingBy { it }.eachCount().any { it.value >= 2 } }
}

private fun part2(input: IntRange) = input.count { value ->
    with(value.digits) { isSorted() && groupingBy { it }.eachCount().any { it.value == 2 } }
}

private val Int.digits get() = toString().toCharArray().map { it.code }
private fun <T : Comparable<T>> Iterable<T>.isSorted() = zipWithNext().all { (i1, i2) -> i1 <= i2 }
