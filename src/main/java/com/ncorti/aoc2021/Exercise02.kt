package com.ncorti.aoc2021

object Exercise02 {

    fun part1(): Int =
        getInputAsTest("02") { split("\n") }
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .fold(0 to 0) { (depth, horizontal), (move, unit) ->
                when (move) {
                    "forward" -> depth to horizontal + unit
                    "up" -> depth - unit to horizontal
                    else -> depth + unit to horizontal
                }
            }
            .let { (depth, horizontal) -> depth * horizontal }

    fun part2(): Int =
        getInputAsTest("02") { split("\n") }
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
            .fold(Triple(0, 0, 0)) { (depth, horizontal, aim), (move, unit) ->
                when (move) {
                    "forward" -> Triple(depth + (aim * unit), horizontal + unit, aim)
                    "up" -> Triple(depth, horizontal, aim - unit)
                    else -> Triple(depth, horizontal, aim + unit)
                }
            }
            .let { (depth, horizontal) -> depth * horizontal }
}

fun main() {
    println(Exercise02.part1())
    println(Exercise02.part2())
}
