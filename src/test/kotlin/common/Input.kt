package cberg.aoc2019.common

import java.io.File

fun readInput(filename: String) = File("input/$filename").readText().trim()
fun readInputLines(filename: String): List<String> = File("input/$filename").readLines()
