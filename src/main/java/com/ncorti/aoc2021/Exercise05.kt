package com.ncorti.aoc2021

import kotlin.math.max
import kotlin.math.min

object Exercise05 {

    private fun getInput() =
        getInputAsTest("05") { split("\n") }.map { it.split(",", " -> ").map(String::toInt) }.map {
            (i0, i1, i2, i3) ->
            if (i2 < i0) {
                listOf(i2, i3, i0, i1)
            } else {
                listOf(i0, i1, i2, i3)
            }
        }

    fun part1(): Int {
        val input = getInput()
        val size = input.maxOf { it.maxOrNull() ?: 0 } + 1
        val grid = Array(size) { IntArray(size) { 0 } }

        input.forEach {
            if (it[0] == it[2]) {
                (min(it[1], it[3])..max(it[1], it[3])).forEach { i -> grid[i][it[0]] += 1 }
            } else if (it[1] == it[3]) {
                (it[0]..it[2]).forEach { i -> grid[it[1]][i] += 1 }
            }
        }

        return grid.sumOf { line -> line.count { it >= 2 } }
    }

    fun part2(): Int {
        val input = getInput()
        val size = input.maxOf { it.maxOrNull() ?: 0 } + 1
        val grid = Array(size) { IntArray(size) { 0 } }

        input.forEach { (i0, i1, i2, i3) ->
            if (i0 == i2) {
                (min(i1, i3)..max(i1, i3)).forEach { i -> grid[i][i0] += 1 }
            } else if (i1 == i3) {
                (i0..i2).forEach { i -> grid[i1][i] += 1 }
            } else if (i0 < i2 && i1 < i3) {
                // Straight diagonal case
                (i0..i2).forEachIndexed { index, _ -> grid[i1 + index][i0 + index] += 1 }
            } else {
                // Inverse diagonal case
                (i0..i2).forEachIndexed { index, _ -> grid[i1 - index][i0 + index] += 1 }
            }
        }

        return grid.sumOf { line -> line.count { it >= 2 } }
    }
}

fun main() {
    println(Exercise05.part1())
    println(Exercise05.part2())
}
