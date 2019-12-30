package cberg.aoc2019.puzzle10

import cberg.aoc2019.coordinate.Coordinate
import cberg.aoc2019.coordinate.manhattanDistance
import cberg.aoc2019.coordinate.minus
import cberg.aoc2019.coordinate.quadrant
import cberg.aoc2019.readInputLines
import org.junit.Test
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
        run {
            val asteroids = parseInput(example1)
            val space = Space(asteroids)
            assertEquals(8, space.getMaxDetectedAsteroids())
        }

        run {
            val asteroids = parseInput(example2)
            val space = Space(asteroids)
            assertEquals(33, space.getMaxDetectedAsteroids())
        }

        run {
            val asteroids = parseInput(example3)
            val space = Space(asteroids)
            assertEquals(35, space.getMaxDetectedAsteroids())
        }

        run {
            val asteroids = parseInput(example4)
            val space = Space(asteroids)
            assertEquals(41, space.getMaxDetectedAsteroids())
        }

        run {
            val asteroids = parseInput(example5)
            val space = Space(asteroids)
            assertEquals(210, space.getMaxDetectedAsteroids())
        }

        run {
            val inputLines = readInputLines("input10.txt")
            val asteroids = parseInput(inputLines)
            val space = Space(asteroids)
            assertEquals(309, space.getMaxDetectedAsteroids())
        }
    }

    @Test
    fun testPart2() {
        run {
            val asteroids = parseInput(example5)
            val space = Space(asteroids)
            assertEquals(802, part2(space))
        }

        run {
            val inputLines = readInputLines("input10.txt")
            val asteroids = parseInput(inputLines)
            val space = Space(asteroids)
            assertEquals(416, part2(space))
        }
    }

    private fun part2(space: Space): Int {
        return space.getVaporizedAsteroid(200).let { (x, y) -> x * 100 + y }
    }
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
        .max() ?: error("No solution found")

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
        val laser = asteroids.maxBy { station ->
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
