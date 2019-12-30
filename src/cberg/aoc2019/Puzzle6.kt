package cberg.aoc2019

import cberg.aoc2019.common.readInputLines
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle6 {
    @Test
    fun testPart1() {
        run {
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
            assertEquals(42, part1(input))
        }

        run {
            val input = readInputLines("input6.txt")
            assertEquals(122782, part1(input))
        }
    }

    @Test
    fun testPart2() {
        run {
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
            assertEquals(4, part2(input))
        }

        run {
            val input = readInputLines("input6.txt")
            assertEquals(271, part2(input))
        }
    }

}

private fun part1(input: List<String>): Int {
    val orbits = parseOrbits(input)
    val numbers = mutableMapOf("COM" to 0)

    fun getNumberOfOrbits(o: String): Int = numbers.getOrPut(o) {
        orbits[o]?.let { getNumberOfOrbits(it) + 1 } ?: error("Unknown object: $o")
    }

    return orbits.keys.sumBy { getNumberOfOrbits(it) }
}

private fun part2(input: List<String>): Int {
    val orbits = parseOrbits(input)
    val youOrbits = generateSequence("YOU") { orbits[it] }.drop(1).toSet()
    val sanOrbits = generateSequence("SAN") { orbits[it] }.drop(1).toSet()
    return (youOrbits - sanOrbits).size + (sanOrbits - youOrbits).size
}

private fun parseOrbits(input: List<String>) = input
    .map { it.split(")") }
    .map { (a, b) -> b to a }
    .toMap()
