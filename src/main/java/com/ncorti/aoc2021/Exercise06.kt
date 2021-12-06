package com.ncorti.aoc2021

object Exercise06 {

    private fun getInput(): MutableList<Long> =
        getInputAsTest("06") { split(",") }.map(String::toInt).let { input ->
            LongArray(9) { i -> input.count { it == i }.toLong() }.toMutableList()
        }

    private fun simulate(input: MutableList<Long>, steps: Int): Long {
        repeat(steps) {
            val zeros = input.removeFirst()
            input.add(zeros)
            input[6] += zeros
        }
        return input.sum()
    }

    fun part1(): Long = simulate(getInput(), 80)

    fun part2(): Long = simulate(getInput(), 256)
}

fun main() {
    println(Exercise06.part1())
    println(Exercise06.part2())
}
