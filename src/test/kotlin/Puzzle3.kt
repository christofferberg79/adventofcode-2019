package cberg.aoc2019

import cberg.aoc2019.common.*
import org.junit.Test
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

class Puzzle3 {
    @Test
    fun testPart1() {
        run {
            val input1 = "R8,U5,L5,D3"
            val input2 = "U7,R6,D4,L4"
            val dist = part1(input1, input2)
            assertEquals(6, dist)
        }

        run {
            val input1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
            val input2 = "U62,R66,U55,R34,D71,R55,D58,R83"
            val dist = part1(input1, input2)
            assertEquals(159, dist)
        }

        run {
            val input1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
            val input2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
            val dist = part1(input1, input2)
            assertEquals(135, dist)
        }

        run {
            val (input1, input2) = readInputLines("input3.txt")
            val dist = part1(input1, input2)
            assertEquals(1285, dist)
        }
    }

    @Test
    fun testPart2() {
        run {
            val input1 = "R8,U5,L5,D3"
            val input2 = "U7,R6,D4,L4"
            val steps = part2(input1, input2)
            assertEquals(30, steps)
        }

        run {
            val input1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
            val input2 = "U62,R66,U55,R34,D71,R55,D58,R83"
            val steps = part2(input1, input2)
            assertEquals(610, steps)
        }

        run {
            val input1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
            val input2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
            val steps = part2(input1, input2)
            assertEquals(410, steps)
        }

        run {
            val (input1, input2) = readInputLines("input3.txt")
            val steps = part2(input1, input2)
            assertEquals(14228, steps)
        }
    }
}

private fun part1(input1: String, input2: String): Int {
    val lines1 = parseToLines(input1)
    val lines2 = parseToLines(input2)

    return lines1.flatMap { line1 ->
        lines2.map { line2 ->
            intersectionOf(
                line1,
                line2
            )
        }
    }
        .filterNotNull()
        .map { it.manhattanDistance }
        .minOrNull() ?: error("No soultion found")
}

private fun part2(input1: String, input2: String): Int {
    val lines1 = parseToLines(input1)
    val lines2 = parseToLines(input2)

    var fewest = Int.MAX_VALUE
    var steps1 = 0
    for (line1 in lines1) {
        var steps2 = 0
        for (line2 in lines2) {
            intersectionOf(line1, line2)?.let { intersection ->
                val delta1 = (intersection - line1.start).manhattanDistance
                val delta2 = (intersection - line2.start).manhattanDistance
                val steps = steps1 + delta1 + steps2 + delta2
                fewest = min(steps, fewest)
            }
            steps2 += line2.dir.manhattanDistance
        }
        steps1 += line1.dir.manhattanDistance
    }
    return fewest
}

private fun parseToLines(input: String): List<Line> {
    var pos = Coordinate(0, 0)
    return input
        .split(",")
        .map { parseDir(it) }
        .map { dir -> Line(pos, dir).also { line -> pos = line.end } }
}

private fun parseDir(s: String): Coordinate {
    val dir = s.first()
    val dist = s.drop(1).toInt()
    return when (dir) {
        'U' -> Coordinate(0, -dist)
        'D' -> Coordinate(0, dist)
        'R' -> Coordinate(dist, 0)
        'L' -> Coordinate(-dist, 0)
        else -> error("Invalid direction $dir")
    }
}

private data class Line(val start: Coordinate, val dir: Coordinate)

private val Line.end get() = start + dir

private fun intersectionOf(line1: Line, line2: Line): Coordinate? {
    fun Int.isBetween(i1: Int, i2: Int) = this in min(i1, i2)..max(i1, i2)

    if (line1.start == line2.start) {
        return null
    }
    if (line1.dir.x == 0 && line2.dir.y == 0) {
        if (line1.start.x.isBetween(line2.start.x, line2.end.x) &&
            line2.start.y.isBetween(line1.start.y, line1.end.y)
        ) {
            return Coordinate(line1.start.x, line2.start.y)
        }
    } else if (line1.dir.y == 0 && line2.dir.x == 0) {
        if (line1.start.y.isBetween(line2.start.y, line2.end.y) &&
            line2.start.x.isBetween(line1.start.x, line1.end.x)
        ) {
            return Coordinate(line2.start.x, line1.start.y)
        }
    }
    return null
}
