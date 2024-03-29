package cberg.aoc2019

import cberg.aoc2019.common.readInputLines
import kotlin.math.abs
import kotlin.math.sign
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle12 {
    @Test
    fun testPart1() {
        run {
            val input = listOf(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>"
            )
            assertEquals(179, part1(input, 10))
        }

        run {
            val input = readInputLines("input12.txt")
            assertEquals(9876, part1(input, 1000))
        }
    }

    @Test
    fun testPart2() {
        run {
            val input = listOf(
                "<x=-1, y=0, z=2>",
                "<x=2, y=-10, z=-7>",
                "<x=4, y=-8, z=8>",
                "<x=3, y=5, z=-1>"
            )
            assertEquals(2772, part2(input))
        }

        run {
            val input = listOf(
                "<x=-8, y=-10, z=0>",
                "<x=5, y=5, z=10>",
                "<x=2, y=-7, z=3>",
                "<x=9, y=-8, z=-3>"
            )
            assertEquals(4686774924, part2(input))
        }

        run {
            val input = readInputLines("input12.txt")
            assertEquals(307043147758488, part2(input))
        }
    }
}

private fun part1(input: List<String>, steps: Int): Int {
    val moons = parseInput(input)
    repeat(steps) {
        step(moons)
    }
    return moons.sumOf { it.energy }
}

private fun part2(input: List<String>): Long {
    val moons = parseInput(input)
    val initialState = parseInput(input)
    val initialX = state1D(initialState) { x }
    val initialY = state1D(initialState) { y }
    val initialZ = state1D(initialState) { z }
    var steps = 0L
    var stepsX = 0L
    var stepsY = 0L
    var stepsZ = 0L
    do {
        step(moons)
        steps++
        if (stepsX == 0L && state1D(moons) { x } == initialX) {
            stepsX = steps
        }
        if (stepsY == 0L && state1D(moons) { y } == initialY) {
            stepsY = steps
        }
        if (stepsZ == 0L && state1D(moons) { z } == initialZ) {
            stepsZ = steps
        }
    } while (stepsX == 0L || stepsY == 0L || stepsZ == 0L)

    return steps(stepsX, stepsY, stepsZ)
}

private fun steps(x: Long, y: Long, z: Long): Long {
    val factorsX = x.primeFactors()
    val factorsY = y.primeFactors()
    val factorsZ = z.primeFactors()
    val primes = factorsX.keys + factorsY.keys + factorsZ.keys
    return primes
        .map { it.pow(maxOf(factorsX[it] ?: 0, factorsY[it] ?: 0, factorsZ[it] ?: 0)) }
        .reduce { a, b -> a * b }
}

private fun Long.primeFactors(): Map<Long, Int> {
    var n = this
    var d = 2L
    val factors = mutableListOf<Long>()
    while (n > 1) {
        while (n % d == 0L) {
            factors += d
            n /= d
        }
        d++
    }
    return factors.groupingBy { it }.eachCount()
}

private fun Long.pow(p: Int) = generateSequence(1L) { it * this }.elementAt(p)

private fun state1D(initialState: List<Moon>, selector: Vec3.() -> Int) =
    initialState.flatMap { listOf(it.position.selector(), it.velocity.selector()) }

private fun step(moons: List<Moon>) {
    applyGravity(moons)
    applyVelocity(moons)
}

private fun applyGravity(moons: List<Moon>) {
    for (moon in moons) {
        for (otherMoon in moons) {
            moon.velocity += Vec3(
                (otherMoon.position.x - moon.position.x).sign,
                (otherMoon.position.y - moon.position.y).sign,
                (otherMoon.position.z - moon.position.z).sign
            )
        }
    }
}

private fun applyVelocity(moons: List<Moon>) = moons.forEach { it.position += it.velocity }

private data class Vec3(val x: Int, val y: Int, val z: Int)

private val Vec3.manhattanDistance get() = abs(x) + abs(y) + abs(z)
private operator fun Vec3.plus(other: Vec3) =
    Vec3(x + other.x, y + other.y, z + other.z)

private fun parseInput(input: List<String>) = input
    .map {
        val (x, y, z) = Regex("<x=(.*), y=(.*), z=(.*)>").matchEntire(it)?.destructured ?: error("Invalid input: $it")
        Moon(Vec3(x.toInt(), y.toInt(), z.toInt()))
    }

private data class Moon(var position: Vec3, var velocity: Vec3) {
    constructor(position: Vec3) : this(
        position,
        Vec3(0, 0, 0)
    )
}

private val Moon.energy get() = position.manhattanDistance * velocity.manhattanDistance
