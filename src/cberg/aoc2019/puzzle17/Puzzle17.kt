package cberg.aoc2019.puzzle17

import cberg.aoc2019.coordinate.Coordinate
import cberg.aoc2019.coordinate.Coordinate.Companion.down
import cberg.aoc2019.coordinate.Coordinate.Companion.left
import cberg.aoc2019.coordinate.Coordinate.Companion.right
import cberg.aoc2019.coordinate.Coordinate.Companion.up
import cberg.aoc2019.coordinate.plus
import cberg.aoc2019.intcode.Intcode
import cberg.aoc2019.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle17 {
    @Test
    fun testPart1() {
        assertEquals(9876, part1())
    }

    @Test
    fun testPart2() {
        assertEquals(1234055, part2())
    }
}

private fun part1(): Int {
    val program = readInput("input17.txt")
    val ic = Intcode(program)
    var x = 0
    var y = 0
    val map = mutableMapOf<Coordinate, Char>()
    while (ic.isRunning) {
        ic.step()
        ic.receiveOutputOrNull()?.let { output ->
            if (output == 10L) {
                x = 0
                y++
            } else {
                map[Coordinate(x++, y)] = output.toChar()
            }
        }
    }

    return map.keys.filter { listOf(it, it + up, it + down, it + right, it + left).all { map[it] == '#' } }
        .map { (x, y) -> x * y }.sum()
}

private fun part2(): Int {

    val input = listOf(
        "A,B,A,C,B,C,B,A,C,B\n",
        "L,10,L,6,R,10\n",
        "R,6,R,8,R,8,L,6,R,8\n",
        "L,10,R,8,R,8,L,10\n",
        "n\n"
    )

    val program = readInput("input17.txt")
    val ic = Intcode(program)
    ic[0] = 2

    input.forEach { it.forEach { ic.sendInput(it.toLong()) } }

    ic.run()

    return ic.receiveAllOutput().last().toInt()
}