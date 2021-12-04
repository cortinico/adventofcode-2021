package com.ncorti.aoc2021

object Exercise03 {

    fun part1(): Int {
        val input =
            getInputAsTest("03") { split("\n") }.map {
                it.toCharArray().map { digit -> digit.digitToInt() }
            }

        return input
            .reduce { l, r -> l.mapIndexed { index, item -> item + r[index] } }
            .fold("" to "") { (gamma, epsilon), count ->
                if (count > input.size / 2) "${gamma}0" to "${epsilon}1"
                else "${gamma}1" to "${epsilon}0"
            }
            .let { (first, second) -> first.toInt(2) * second.toInt(2) }
    }

    fun part2(): Int {
        val input = getInputAsTest("03") { split("\n") }

        return IntArray(input[0].length)
            .foldIndexed(input to input) { index, (oxygen, co2), _ ->
                val mostCommonInOxygen =
                    if ((oxygen.count { it[index] == '1' }) >= oxygen.size / 2.0) '1' else '0'
                val mostCommonInCo2 =
                    if ((co2.count { it[index] == '1' }) >= co2.size / 2.0) '1' else '0'
                val newOxygen =
                    if (oxygen.size == 1) {
                        oxygen
                    } else {
                        oxygen.filter { it[index] == mostCommonInOxygen }
                    }
                val newCo2 =
                    if (co2.size == 1) {
                        co2
                    } else {
                        co2.filter { it[index] != mostCommonInCo2 }
                    }
                newOxygen to newCo2
            }
            .let { (first, second) -> first[0].toInt(2) * second[0].toInt(2) }
    }
}

fun main() {
    println(Exercise03.part1())
    println(Exercise03.part2())
}
