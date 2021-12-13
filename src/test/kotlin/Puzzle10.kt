package cberg.aoc2019

import cberg.aoc2019.common.Coordinate
import cberg.aoc2019.common.manhattanDistance
import cberg.aoc2019.common.minus
import cberg.aoc2019.common.quadrant
import cberg.aoc2019.common.readInputLines
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle10 {
    private val example1 = listOf(
        ".#..#",
        ".....",
        "#####",
        "....#",
        "...##"
    )

    private val example2 = listOf(
        "......#.#.",
        "#..#.#....",
        "..#######.",
        ".#.#.###..",
        ".#..#.....",
        "..#....#.#",
        "#..#....#.",
        ".##.#..###",
        "##...#..#.",
        ".#....####"
    )

    private val example3 = listOf(
        "#.#...#.#.",
        ".###....#.",
        ".#....#...",
        "##.#.#.#.#",
        "....#.#.#.",
        ".##..###.#",
        "..#...##..",
        "..##....##",
        "......#...",
        ".####.###."
    )

    private val example4 = listOf(
        ".#..#..###",
        "####.###.#",
        "....###.#.",
        "..###.##.#",
        "##.##.#.#.",
        "....###..#",
        "..#.#..#.#",
        "#..#.#.###",
        ".##...##.#",
        ".....#.#.."
    )

    private val example5 = listOf(
        ".#..##.###...#######",
        "##.############..##.",
        ".#.######.########.#",
        ".###.#######.####.#.",
        "#####.##.#.##.###.##",
        "..#####..#.#########",
        "####################",
        "#.####....###.#.#.##",
        "##.#################",
        "#####.##.###..####..",
        "..######..##.#######",
        "####.##.####...##..#",
        ".#####..#.######.###",
        "##...#.##########...",
        "#.##########.#######",
        ".####.#.###.###.#.##",
        "....##.##.###..#####",
        ".#.#.###########.###",
        "#.#.#.#####.####.###",
        "###.##.####.##.#..##"
    )

    @Test
    fun testPart1() {
        assertEquals(8, part1(example1))
        assertEquals(33, part1(example2))
        assertEquals(35, part1(example3))
        assertEquals(41, part1(example4))
        assertEquals(210, part1(example5))

        val input = readInputLines("input10.txt")
        assertEquals(309, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(802, part2(example5))

        val input = readInputLines("input10.txt")
        assertEquals(416, part2(input))
    }
}

private fun part1(input: List<String>): Int {
    return input.toSpace().getMaxDetectedAsteroids()
}

private fun part2(input: List<String>): Int {
    return input.toSpace().getVaporizedAsteroid(200).let { (x, y) -> x * 100 + y }
}

private fun List<String>.toSpace(): Space {
    val asteroids = parseInput(this)
    return Space(asteroids)
}

private fun parseInput(input: List<String>) = input
    .mapIndexed { y, inputLine ->
        inputLine.mapIndexed { x, c -> if (c == '#') Coordinate(x, y) else null }
    }
    .flatten()
    .filterNotNull()

private class Space(asteroids: List<Coordinate>) {
    val asteroids = asteroids.toMutableList()

    fun getMaxDetectedAsteroids() = asteroids
        .map { station -> asteroids.count { station.canDetect(it) } }
        .maxOrNull() ?: error("No solution found")

    private fun Coordinate.canDetect(asteroid: Coordinate): Boolean {
        if (asteroid == this) return false
        return asteroids.none { it.isBetween(this, asteroid) }
    }

    private fun Coordinate.isBetween(c1: Coordinate, c2: Coordinate): Boolean {
        if (this == c1 || this == c2) return false
        val vec1 = c1 - this
        val vec2 = c1 - c2
        return vec1.x * vec2.y == vec1.y * vec2.x &&
                vec1.quadrant == vec2.quadrant &&
                vec1.manhattanDistance < vec2.manhattanDistance
    }

    fun getVaporizedAsteroid(n: Int): Coordinate {
        val laser = asteroids.maxByOrNull { station ->
            asteroids.count { station.canDetect(it) }
        } ?: error("No solution found")

        asteroids.remove(laser)

        asteroids.sortWith(
            compareBy(
                { (it - laser).quadrant },
                { (it - laser).run { y.toDouble() / x.toDouble() } })
        )

        var leftToVaporize = n
        while (true) {
            val vaporized = asteroids.filter { laser.canDetect(it) }
            if (leftToVaporize > vaporized.size) {
                asteroids.removeAll(vaporized)
                leftToVaporize -= vaporized.size
            } else {
                return vaporized[leftToVaporize - 1]
            }
        }
    }
}
