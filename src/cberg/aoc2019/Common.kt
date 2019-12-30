package cberg.aoc2019

import java.io.File

fun readInput(filename: String) = File("input/$filename").readText().trim()
fun readInputLines(filename: String): List<String> = File("input/$filename").readLines()
