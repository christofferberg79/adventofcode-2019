package cberg.aoc2019

import cberg.aoc2019.common.Coordinate
import cberg.aoc2019.common.Coordinate.Companion.up
import cberg.aoc2019.common.plus
import cberg.aoc2019.common.turnLeft
import cberg.aoc2019.common.turnRight
import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle11 {

    @Test
    fun testPart1() {
        val input = readInput("input11.txt")
        assertEquals(1709, part1(input))
    }

    @Test
    fun testPart2() {
        val expected = listOf( // PGUEHCJH
            " ###   ##  #  # #### #  #  ##    ## #  #   ",
            " #  # #  # #  # #    #  # #  #    # #  #   ",
            " #  # #    #  # ###  #### #       # ####   ",
            " ###  # ## #  # #    #  # #       # #  #   ",
            " #    #  # #  # #    #  # #  # #  # #  #   ",
            " #     ###  ##  #### #  #  ##   ##  #  #   "
        )
        val input = readInput("input11.txt")
        assertEquals(expected, part2(input))
    }
}

private fun part1(input: String): Int {
    val grid = mutableMapOf<Coordinate, Long>()
    paint(grid, input)
    return grid.size
}

private fun part2(input: String): List<String> {
    val grid = mutableMapOf(Coordinate(0, 0) to 1L)

    paint(grid, input)

    val minX = grid.keys.map { it.x }.min() ?: error("Empty grid")
    val minY = grid.keys.map { it.y }.min() ?: error("Empty grid")
    val maxX = grid.keys.map { it.x }.max() ?: error("Empty grid")
    val maxY = grid.keys.map { it.y }.max() ?: error("Empty grid")

    val output = mutableListOf<Char>()
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            output += if (grid[Coordinate(x, y)] ?: 0L == 0L) ' ' else '#'
        }
    }
    return output.chunked(maxX - minX + 1).map { it.joinToString(separator = "") }
}

private fun paint(grid: MutableMap<Coordinate, Long>, program: String) {
    val ic = Intcode(program)
    var pos = Coordinate(0, 0)
    var dir = up
    var painting = true


    ic.sendInput(grid[pos] ?: 0L)

    while (ic.isRunning) {
        ic.run()
        ic.receiveOutput().forEach { outputValue ->
            if (painting) {
                grid[pos] = outputValue
            } else {
                dir = when (outputValue) {
                    0L -> dir.turnLeft()
                    1L -> dir.turnRight()
                    else -> error("Invalid turn code")
                }
                pos += dir
                ic.sendInput(grid[pos] ?: 0L)
            }
            painting = !painting
        }
    }
}
