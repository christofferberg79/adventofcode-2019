package cberg.aoc2019

import cberg.aoc2019.common.Coordinate
import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle19 {
    @Test
    fun testPart1() {
        val input = readInput("input19.txt")
        assertEquals(234, part1(input))
    }

    @Test
    fun testPart2() {
        val input = readInput("input19.txt")
        assertEquals(9290812, part2(input))
    }

}

private fun part1(program: String) = Beam(program).let { beam ->
    val grid = getGrid(Coordinate(0, 0), Coordinate(49, 49))
    grid.count { pos -> beam.hits(pos) }
}

private fun part2(program: String): Int {
    val beam = Beam(program)
    var y = 99
    var x = 0
    while (true) {
        while (!beam.hits(Coordinate(x, y))) {
            x++
        }
        if (beam.hits(Coordinate(x + 99, y - 99))) {
            return x * 10000 + y - 99
        } else {
            y++
        }
    }
}

private class Beam(private val program: String) {
    fun hits(c: Coordinate): Boolean {
        val ic = Intcode(program)
        ic.sendInput(c.x.toLong())
        ic.sendInput(c.y.toLong())
        ic.run()
        return ic.receiveOutput().first() == 1L
    }

}

private fun getGrid(topLeft: Coordinate, bottomRight: Coordinate) =
    (topLeft.y..bottomRight.y).flatMap { y ->
        (topLeft.x..bottomRight.x).map { x ->
            Coordinate(x, y)
        }
    }
