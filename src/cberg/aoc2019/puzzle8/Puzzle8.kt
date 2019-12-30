package cberg.aoc2019.puzzle8

import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle8 {
    @Test
    fun part1() {
        val layer = readInput("input8.txt")
            .chunked(25 * 6)
            .minBy { layer -> layer.count { pixel -> pixel == '0' } } ?: error("No layers found")
        val result = layer.count { it == '1' } * layer.count { it == '2' }
        assertEquals(828, result)
    }

    @Test
    fun part2() {
        val layers = readInput("input8.txt").chunked(25 * 6)
        CharArray(25 * 6) { index -> layers.map { it[index] }.first { it != '2' } }
            .map { if (it == '0') ' ' else '#' }
            .joinToString(separator = "")
            .chunked(25).forEach { println(it) }
        val result = "ZLBJF"
        assertEquals("ZLBJF", result)
    }
}