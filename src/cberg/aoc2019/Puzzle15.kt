package cberg.aoc2019

import cberg.aoc2019.common.Coordinate
import cberg.aoc2019.common.Coordinate.Companion.down
import cberg.aoc2019.common.Coordinate.Companion.left
import cberg.aoc2019.common.Coordinate.Companion.right
import cberg.aoc2019.common.Coordinate.Companion.up
import cberg.aoc2019.common.plus
import cberg.aoc2019.common.unaryMinus
import cberg.aoc2019.common.Intcode
import cberg.aoc2019.Status.*
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle15 {
    @Test
    fun testPart1() {
        val program = readInput("input15.txt")
        val droid = Droid(program)
        assertEquals(424, droid.findO2Sys())
    }

    @Test
    fun testPart2() {
        val program = readInput("input15.txt")
        val droid = Droid(program)
        assertEquals(446, droid.fillWithOxygen())
    }
}

private class Droid(program: String) {
    private val ic = Intcode(program)

    private var pos = Coordinate(0, 0)
    private val map = mutableMapOf(pos to Location(EMPTY))

    fun findO2Sys(): Int {
        buildMap()
        return map.values.first { it.status == O2_SYS }.stepsFromStart
    }

    fun fillWithOxygen(): Int {
        buildMap()
        pos = map.filterValues { it.status == O2_SYS }.keys.single()
        map[pos] = Location(O2_SYS)
        map.filterValues { it.status == EMPTY }.forEach { (pos, loc) -> map[pos] =
            Location(loc.status, Int.MAX_VALUE)
        }
        buildMap()

        return map.values.filter { it.status == EMPTY }.map { it.stepsFromStart }.max() ?: error("No solution found")
    }

    private fun buildMap() {
        while (true) {
            val dirs = mutableListOf(up, right, down, left)
            val loc = map[pos] ?: error("I don't know where I am")
            loc.back?.let { dirs.remove(it) }
            var moved = false
            for (dir in dirs) {
                val potLoc = map[pos + dir]
                if (potLoc == null) {
                    val newStatus = move(dir)
                    val newLoc = Location(newStatus, loc.stepsFromStart + 1, -dir)
                    map[pos + dir] = newLoc
                    if (newStatus != WALL) {
                        pos += dir
                        moved = true
                    }
                } else if (potLoc.status != WALL && potLoc.stepsFromStart > loc.stepsFromStart + 1) {
                    pos += dir
                    map[pos] = Location(potLoc.status, loc.stepsFromStart + 1, -dir)
                    moved = true
                }
                if (moved) {
                    break
                }
            }
            if (!moved) {
                loc.back?.let { back ->
                    move(back)
                    pos += back
                } ?: break
            }
        }
    }

    private fun drawMap() {
        val o2SysPos = map.filterValues { it.status == O2_SYS }.keys.single()
        val pathToO2Sys = generateSequence(o2SysPos) { map[it]?.back?.plus(it) }.toSet()

        val minX = map.keys.map { it.x }.min() ?: 0
        val maxX = map.keys.map { it.x }.max() ?: 0
        val minY = map.keys.map { it.y }.min() ?: 0
        val maxY = map.keys.map { it.y }.max() ?: 0
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val c = when (val drawPos = Coordinate(x, y)) {
                    o2SysPos -> "X"
                    pos -> "D"
                    in pathToO2Sys -> "."
                    else -> when (map[drawPos]?.status) {
                        null -> " "
                        EMPTY -> " "
                        WALL -> "#"
                        O2_SYS -> "X"
                    }
                }
                print(c)
            }
            println()
        }
    }

    private fun move(dir: Coordinate): Status {
        ic.sendInput(dir.toInput())
        while (!ic.hasOutput()) {
            ic.step()
        }
        return ic.receiveOutput().toStatus()
    }


    private fun Coordinate.toInput() = when (this) {
        up -> 1L
        down -> 2L
        left -> 3L
        right -> 4L
        else -> error("Invalid direction: $this")
    }

    private fun Long.toStatus() = when (this) {
        0L -> WALL
        1L -> EMPTY
        2L -> O2_SYS
        else -> error("Unknown status: $this")
    }
}

private enum class Status { WALL, EMPTY, O2_SYS }

private data class Location(val status: Status, val stepsFromStart: Int = 0, val back: Coordinate? = null)
