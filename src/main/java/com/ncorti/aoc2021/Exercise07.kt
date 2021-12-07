package com.ncorti.aoc2021

import kotlin.math.abs
import kotlin.math.min

object Exercise07 {

    fun part1(): Int =
        getInputAsTest("07") { split(",") }.map(String::toInt).let { input ->
            input.minOf { element -> input.sumOf { abs(it - element) } }
        }

    fun part2(): Int {
        val input = getInputAsTest("07") { split(",") }.map(String::toInt)
        var minCost = Int.MAX_VALUE
        for (element in input.minOrNull()!!..input.maxOrNull()!!) {
            input.sumOf { ((abs(it - element) + 1) * abs(it - element)) / 2 }.let {
                minCost = min(it, minCost)
            }
        }
        return minCost
    }
}

fun main() {
    println(Exercise07.part1())
    println(Exercise07.part2())
}
