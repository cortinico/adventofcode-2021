package com.ncorti.aoc2021

fun part1(): Int {
    val input = getInputAsTest("01").split("\n")

    val numberSet = input.toSet().map { it.toInt() }
    return numberSet.first { (2020 - it) in numberSet }.let { it * (2020 - it) }
}

fun part2(): Int {
    val input = getInputAsTest("01").split("\n")

    val numberSet = input.map { it.toInt() }.toSet()

    numberSet.forEachIndexed { index1, num1 ->
        numberSet.forEachIndexed { index2, num2 ->
            if (index1 != index2 && 2020 - num1 - num2 in numberSet) {
                return num1 * num2 * (2020 - num1 - num2)
            }
        }
    }
    error("Response not found!")
}

fun main() {
    println(part1())
    println(part2())
}
