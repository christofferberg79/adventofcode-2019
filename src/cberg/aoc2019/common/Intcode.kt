package cberg.aoc2019.common

class Intcode(program: List<Long>) {
    constructor(program: String) : this(parseProgram(program))

    private val program = program.mapIndexed { index, value -> index.toLong() to value }.toMap(mutableMapOf())
    private val input = mutableListOf<Long>()
    private val output = mutableListOf<Long>()
    private val pos = Position(this)

    var isFinished = false
        private set
    var isWaitingForInput = false
        private set
    val isRunning: Boolean get() = !(isFinished || isWaitingForInput && input.isEmpty())

    operator fun get(index: Long) = program[index] ?: 0
    operator fun set(index: Long, value: Long) {
        program[index] = value
    }

    fun toList() = List(program.keys.max()?.toInt()?.plus(1) ?: 0) { i -> get(i.toLong()) }

    fun sendInput(value: Long) {
        input += value
    }

    fun receiveOutput() = output.removeAt(0)
    fun receiveOutputOrNull() = if (hasOutput()) receiveOutput() else null
    fun hasOutput() = output.isNotEmpty()
    fun receiveAllOutput() = generateSequence { receiveOutputOrNull() }

    fun run() {
        while (isRunning) {
            step()
        }
    }

    fun step() {
        if (isWaitingForInput) {
            input()
        } else {
            processOpcode()
        }
    }

    private fun processOpcode() = when (pos.opcode) {
        1L -> add()
        2L -> multiply()
        3L -> input()
        4L -> output()
        5L -> jumpIfTrue()
        6L -> jumpIfFalse()
        7L -> lessThan()
        8L -> equals()
        9L -> adjustRelBase()
        99L -> halt()
        else -> error("unknown opcode ${pos.opcode}")
    }

    private fun add() {
        pos[3] = pos[1] + pos[2]
        pos += 4
    }

    private fun multiply() {
        pos[3] = pos[1] * pos[2]
        pos += 4
    }

    private fun input() {
        if (input.isEmpty()) {
            isWaitingForInput = true
        } else {
            pos[1] = input.removeAt(0)
            pos += 2
            isWaitingForInput = false
        }
    }

    private fun output() {
        output += pos[1]
        pos += 2
    }

    private fun jumpIfTrue() {
        if (pos[1] != 0L) {
            pos.moveTo(pos[2])
        } else {
            pos += 3
        }
    }

    private fun jumpIfFalse() {
        if (pos[1] == 0L) {
            pos.moveTo(pos[2])
        } else {
            pos += 3
        }
    }

    private fun lessThan() {
        pos[3] = if (pos[1] < pos[2]) 1L else 0L
        pos += 4
    }

    private fun equals() {
        pos[3] = if (pos[1] == pos[2]) 1L else 0L
        pos += 4
    }

    private fun adjustRelBase() {
        pos.adjustRelBase(pos[1])
        pos += 2
    }

    private fun halt() {
        isFinished = true
    }

}

private class Position(private val program: Intcode) {
    private var pos = 0L
    private var relBase = 0L

    private val instruction get() = program[pos]
    val opcode get() = instruction % 100

    operator fun get(offset: Int): Long {
        return program[position(offset)]
    }

    operator fun set(offset: Int, value: Long) {
        program[position(offset)] = value
    }

    private fun position(offset: Int): Long {
        return when (instruction / (10.pow(offset + 1)) % 10) {
            0L -> program[pos + offset]
            1L -> pos + offset
            else -> relBase + program[pos + offset]
        }
    }

    fun adjustRelBase(offset: Long) {
        relBase += offset
    }

    operator fun plusAssign(offset: Long) {
        pos += offset
    }

    fun moveTo(pos: Long) {
        this.pos = pos
    }
}

private fun Int.pow(n: Int) = generateSequence(1) { it * this }.elementAt(n)

private fun parseProgram(s: String) = s.split(",").map(String::toLong)