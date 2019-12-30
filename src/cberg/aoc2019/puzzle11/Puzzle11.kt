package cberg.aoc2019.puzzle11

import cberg.aoc2019.coordinate.Coordinate
import cberg.aoc2019.coordinate.Coordinate.Companion.up
import cberg.aoc2019.coordinate.plus
import cberg.aoc2019.coordinate.turnLeft
import cberg.aoc2019.coordinate.turnRight
import cberg.aoc2019.intcode.Intcode
import cberg.aoc2019.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle11 {

    @Test
    fun testPart1() {
        val grid = mutableMapOf<Coordinate, Long>()
        paint(grid)
        assertEquals(1709, grid.size)
    }

    @Test
    fun testPart2() {
        val grid = mutableMapOf(Coordinate(0, 0) to 1L)

        paint(grid)

        val minX = grid.keys.map { it.x }.min() ?: error("Empty grid")
        val minY = grid.keys.map { it.y }.min() ?: error("Empty grid")
        val maxX = grid.keys.map { it.x }.max() ?: error("Empty grid")
        val maxY = grid.keys.map { it.y }.max() ?: error("Empty grid")

        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(if (grid[Coordinate(x, y)] ?: 0L == 0L) ' ' else '#')
            }
            println()
        }
        val result = "PGUEHCJH"
        assertEquals("PGUEHCJH", result)
    }

    private fun paint(grid: MutableMap<Coordinate, Long>) {
        val ic = Intcode(readInput("input11.txt"))
        var pos = Coordinate(0, 0)
        var dir = up
        var painting = true


        ic.sendInput(grid[pos] ?: 0L)

        while (ic.isRunning) {
            ic.step()
            ic.receiveOutputOrNull()?.let { outputValue ->
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
}
