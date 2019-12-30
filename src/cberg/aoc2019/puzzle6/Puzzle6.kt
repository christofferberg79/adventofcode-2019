package cberg.aoc2019.puzzle6

import cberg.aoc2019.readInputLines
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle6 {
    @Test
    fun part1() {
        val input = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L"
        )
        assertEquals(42, findNumberOfOrbits(input))

        assertEquals(122782, findNumberOfOrbits(readInputLines("input6.txt")))
    }

    @Test
    fun part2() {
        val input = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L",
            "K)YOU",
            "I)SAN"
        )
        assertEquals(4, findMinimalNumberOfTransfers(input))
        assertEquals(271, findMinimalNumberOfTransfers(readInputLines("input6.txt")))
    }

}

private fun findNumberOfOrbits(input: List<String>): Int {
    val orbits = parseOrbits(input)
    val numbers = mutableMapOf("COM" to 0)

    fun getNumberOfOrbits(o: String): Int = numbers.getOrPut(o) {
        orbits[o]?.let { getNumberOfOrbits(it) + 1 } ?: error(o)
    }

    return orbits.keys.sumBy { getNumberOfOrbits(it) }
}

private fun parseOrbits(input: List<String>) =
    input.map { it.split(")").let { it.last() to it.first() } }.toMap()

private fun findMinimalNumberOfTransfers(input: List<String>): Int {
    val orbits = parseOrbits(input)
    val youCounts = mutableMapOf<String, Int>()
    var dest = orbits["YOU"]
    while (dest != null) {
        youCounts[dest] = youCounts.size
        dest = orbits[dest]
    }

    dest = orbits["SAN"]
    var sanCount = 0
    while (dest != null) {
        youCounts[dest]?.let { youCount -> return sanCount + youCount}
        sanCount++
        dest = orbits[dest]
    }
    error("No solution found")
}
