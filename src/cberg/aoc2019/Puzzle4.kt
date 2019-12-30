package cberg.aoc2019

import org.junit.Test
import kotlin.test.assertEquals

class Puzzle4 {
    @Test
    fun part1() {
        assertEquals(1099, (245182..790572).count { value ->
            with(value.digits) {
                isSorted() && groupingBy { it }.eachCount().any { it.value >= 2 }
            }
        })
    }

    @Test
    fun part2() {
        assertEquals(710, (245182..790572).count { value ->
            with(value.digits) {
                isSorted() && groupingBy { it }.eachCount().any { it.value == 2 }
            }
        })
    }
}

private val Int.digits get() = toString().toCharArray().map { it.toInt() }
private fun <T : Comparable<T>> Iterable<T>.isSorted() = zipWithNext().all { (i1, i2) -> i1 <= i2 }
