package cberg.aoc2019.puzzle9

import cberg.aoc2019.intcode.Intcode
import cberg.aoc2019.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Puzzle9 {
    @Test
    fun testPart1() {
        val program1 = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
        assertEquals(program1, execute(program1))

        val program2 = "1102,34915192,34915192,7,4,7,99,0"
        assertEquals(16, execute(program2).length)

        val program3 = "104,1125899906842624,99"
        assertEquals("1125899906842624", execute(program3))

        val program = readInput("input9.txt")
        assertEquals("3507134798", execute(program, 1))
    }

    @Test
    fun testPart2() {
        val program = readInput("input9.txt")
        assertEquals("84513", execute(program, 2))
    }

}

private fun execute(program: String, vararg input: Long): String {
    val amp = Intcode(program)
    input.forEach(amp::sendInput)
    amp.run()
    return amp.receiveAllOutput().joinToString(separator = ",")
}
