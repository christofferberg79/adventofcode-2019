package cberg.aoc2019.puzzle16

import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.math.abs
import kotlin.test.assertEquals

class Puzzle16 {
    @Test
    fun testPart1() {
        assertEquals("01029498", part1("12345678", 4))
        assertEquals("24176176", part1("80871224585914546619083218645595", 100))
        assertEquals("73745418", part1("19617804207202209144916044189917", 100))
        assertEquals("52432133", part1("69317163492948606335995924319873", 100))
        assertEquals("70856418", part1(readInput("input16.txt"), 100))
    }

    @Test
    fun testPart2() {
        assertEquals("84462026", part2("03036732577212944063491565474664", 100))
        assertEquals("78725270", part2("02935109699940807407585447034323", 100))
        assertEquals("53553731", part2("03081770884921959731165446850517", 100))
        assertEquals("87766336", part2(readInput("input16.txt"), 100))
    }
}

private fun part1(input: String, phases: Int): String {
    return fft(input, phases).take(8).joinToString(separator = "")
}

private fun part2(input: String, phases: Int): String {
    val fft2 = fft2(input, phases)
    val take = fft2.take(8)
    return take.joinToString(separator = "")
}

private fun fft(input: String, phases: Int): List<Int> {
    var digits = input.map { it.toString().toInt() }
    val p = listOf(0, 1, 0, -1)
    repeat(phases) {
        digits = List(digits.size) { i ->
            var sum = 0
            digits.forEachIndexed { j, v -> sum += v * p[(j + 1) / (i + 1) % p.size] }
            abs(sum) % 10
        }
    }
    return digits
}

private fun fft2(input: String, phases: Int): List<Int> {
    val offset = input.take(7).toInt()
    require(offset > input.length * 10000 / 2)
    var digits = input.map { it.toString().toInt() }.let { List(it.size * 10000) { i -> it[i % it.size] } }.drop(offset)
    repeat(phases) {
        var v = 0
        digits = List(digits.size) { i ->
            v += digits[digits.lastIndex - i]
            v % 10
        }.reversed()
    }
    return digits
}