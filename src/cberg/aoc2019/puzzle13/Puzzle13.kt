package cberg.aoc2019.puzzle13

import cberg.aoc2019.common.Intcode
import cberg.aoc2019.common.readInput
import org.junit.Test
import kotlin.math.sign
import kotlin.test.assertEquals

class Puzzle13 {
    @Test
    fun testPart1() {
        val ic = Intcode(readInput("input13.txt"))
        ic.run()
        assertEquals(398, ic.receiveAllOutput().chunked(3).count { it.last() == 2L })
    }

    @Test
    fun testPart2() {
        val ic = Intcode(readInput("input13.txt"))
        ic[0] = 2
        var score = 0L
        var ballX = 0L
        var paddleX = 0L
        while (!ic.isFinished) {
            ic.run()
            val output = ic.receiveAllOutput()
            output.chunked(3).forEach { (x, y, z) ->
                if (x == -1L && y == 0L) {
                    score = z
                }
                if (z == 3L) {
                    paddleX = x
                }
                if (z == 4L) {
                    ballX = x
                }
            }
            if (ic.isWaitingForInput) {
                ic.sendInput((ballX - paddleX).sign.toLong())
            }
        }
        assertEquals(19447, score)
    }
}
