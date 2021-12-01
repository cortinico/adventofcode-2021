package com.ncorti.aoc2021

fun part1(): Int =
    getInputAsTest("01") { split("\n") }
        .map(String::toInt)
        .zipWithNext { l, r -> l - r }
        .count { it < 0 }

fun part2(): Int =
    getInputAsTest("01") { split("\n") }
        .map(String::toInt)
        .windowed(3) { it.sum() }
        .zipWithNext { l, r -> l - r }
        .count { it < 0 }

fun main() {
    println(part1())
    println(part2())
}
