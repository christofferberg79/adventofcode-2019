package cberg.aoc2019.puzzle7

import cberg.aoc2019.intcode.Intcode
import cberg.aoc2019.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle7 {
    @Test
    fun part1() {
        run {
            val signal = part1("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0")
            assertEquals(43210, signal)
        }

        run {
            val signal = part1("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0")
            assertEquals(54321, signal)
        }

        run {
            val signal = part1(
                "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33," +
                        "1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"
            )
            assertEquals(65210, signal)
        }

        run {
            val signal = part1(readInput("input7.txt"))
            assertEquals(95757, signal)
        }
    }

    private fun part1(input: String): Long {
        return getAllPhaseSettingSequences(0L..4L)
            .map { getThrusterSignal(input, it) }
            .max() ?: error("No solution found")
    }

    @Test
    fun part2() {
        run {
            val signal = part2(
                "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"
            )
            assertEquals(139629729, signal, "Example 1")
        }

        run {
            val signal = part2(
                "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53," +
                        "54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"
            )
            assertEquals(18216, signal, "Example 2")
        }

        run {
            val signal = part2(readInput("input7.txt"))
            assertEquals(4275738, signal)
        }
    }

    private fun part2(input: String): Long {
        return getAllPhaseSettingSequences(5L..9L)
            .map { getThrusterSignalWithFeedback(input, it) }
            .max() ?: error("No solution found")
    }
}

private fun getAllPhaseSettingSequences(range: LongRange): List<List<Long>> {
    var lists = listOf(emptyList<Long>())
    for (size in 0..4) {
        val newLists = mutableListOf<List<Long>>()
        for (digit in range) {
            for (list in lists) {
                if (digit !in list) {
                    newLists.add(list + digit)
                }
            }
        }
        lists = newLists
    }
    return lists.toList()
}

private fun getThrusterSignal(program: String, phaseSettings: List<Long>): Long {
    val amps = phaseSettings.map { Intcode(program).apply { sendInput(it) } }

    var signal = 0L
    amps.forEach { amp ->
        amp.sendInput(signal)
        while (!amp.hasOutput()) {
            amp.step()
        }
        signal = amp.receiveOutput()
    }
    return signal
}

private fun getThrusterSignalWithFeedback(program: String, phaseSettings: List<Long>): Long {
    val amps = phaseSettings.map { Intcode(program).apply { sendInput(it) } }
    val next = amps.drop(1) + amps.first()
    amps.first().sendInput(0)

    var signal = 0L
    while (amps.any { it.isRunning }) {
        amps.forEachIndexed { index, amp ->
            amp.step()
            if (amp.hasOutput()) {
                signal = amp.receiveOutput()
                next[index].sendInput(signal)
            }
        }
    }
    return signal
}
