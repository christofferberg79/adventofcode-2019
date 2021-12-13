package cberg.aoc2019

import cberg.aoc2019.common.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle8 {
    @Test
    fun testPart1() {
        val input = readInput("input8.txt")
        val result = part1(input)
        assertEquals(828, result)
    }

    @Test
    fun testPart2() {
        val input = readInput("input8.txt")
        val result = part2(input)
        val expected = listOf( // ZLBJF
            "#### #    ###    ## #### ",
            "   # #    #  #    # #    ",
            "  #  #    ###     # ###  ",
            " #   #    #  #    # #    ",
            "#    #    #  # #  # #    ",
            "#### #### ###   ##  #    "
        )
        assertEquals(expected, result)
    }
}

private fun part1(input: String): Int {
    val layerWithFewestZeros = input.chunked(25 * 6)
        .minByOrNull { layer -> layer.count { pixel -> pixel == '0' } } ?: error("No layers found")
    return layerWithFewestZeros.run { count { it == '1' } * count { it == '2' } }
}

private fun part2(input: String): List<String> {
    val layers = input.chunked(25 * 6)
    return List(25 * 6) { index -> layers.map { it[index] }.first { it != '2' } }
        .map { if (it == '0') ' ' else '#' }
        .joinToString(separator = "")
        .chunked(25)
}
