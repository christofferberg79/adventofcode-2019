package cberg.aoc2019

import cberg.aoc2019.common.readInputLines
import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle22 {
    @Test
    fun testPart1() {
        run {
            val input = listOf(
                "deal into new stack",
                "cut -2",
                "deal with increment 7",
                "cut 8",
                "cut -4",
                "deal with increment 7",
                "cut 3",
                "deal with increment 9",
                "deal with increment 3",
                "cut -1"
            )
            val result = MutableList(10) { 0 }
            for (value in 0..9) {
                result[part1(input, 10, value)] = value
            }

            assertEquals(listOf(9, 2, 5, 8, 1, 4, 7, 0, 3, 6), result)
        }

        run {
            val input = readInputLines("input22.txt")
            assertEquals(4086, part1(input, 10007, 2019))
        }
    }
}

private fun part1(input: List<String>, numCards: Int, value: Int) =
    input.fold(value) { index, instruction ->
        when {
            instruction == "deal into new stack" -> numCards - index - 1
            instruction.startsWith("deal with increment") -> (index * instruction.n) % numCards
            instruction.startsWith("cut") -> (index + numCards - instruction.n) % numCards
            else -> error("Unknown instruction: $instruction")
        }
    }

private val String.n get() = substringAfterLast(' ').toInt()
