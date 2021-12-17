package com.ncorti.aoc2021

import kotlin.math.abs
import kotlin.math.max

object Exercise17 {

    private fun getInput() =
        getInputAsTest("17") {
            removePrefix("target area: x=").split(", y=").flatMap {
                it.split("..").map(String::toInt)
            }
        }

    private val steps = listOf(0 to 1, 1 to 1, 1 to 0)

    private fun simulate(startx: Int, starty: Int, area: List<Int>): Pair<Boolean, Int> {
        var vx = startx
        var vy = starty
        var x = 0
        var y = 0
        var maxy = Int.MIN_VALUE
        while (true) {
            x += vx
            y += vy
            vy--
            vx +=
                when {
                    vx > 0 -> -1
                    vx < 0 -> 1
                    else -> 0
                }
            maxy = max(maxy, y)
            if (x in area[0]..area[1] && y in area[2]..area[3]) {
                return true to maxy
            } else if (y < area[2]) {
                return false to Int.MIN_VALUE
            }
        }
    }

    private fun discoverLandingPoints(
        input: List<Int>,
        seen: MutableSet<Pair<Int, Int>> = mutableSetOf()
    ): Pair<Int, Int> {
        val queue = mutableListOf(0 to 0)
        var maxy = Int.MIN_VALUE
        var foundFirstHit = false
        var count = 0
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst().apply { seen.add(this) }
            val (hit, height) = simulate(x, y, input)
            if (hit) {
                count++
                maxy = max(maxy, height)
                foundFirstHit = true
                for (k in 0..abs(input[0] - input[1])) {
                    for (l in 0..abs(input[0] - input[1])) {
                        (x + k to y + l).apply { queue.addIfNotSeen(this, seen) }
                    }
                }
            }
            if (!foundFirstHit) {
                steps.forEach { (incx, incy) ->
                    (x + incx to y + incy).apply { queue.addIfNotSeen(this, seen) }
                }
            }
        }
        return maxy to count
    }

    private fun MutableList<Pair<Int, Int>>.addIfNotSeen(
        pair: Pair<Int, Int>,
        seen: MutableSet<Pair<Int, Int>>
    ) {
        if (pair !in this && pair !in seen) {
            this.add(pair)
        }
    }

    fun part1() = discoverLandingPoints(getInput()).first

    fun part2(): Int {
        val input = getInput()
        val seen = mutableSetOf<Pair<Int, Int>>()
        var count = 0
        for (i in 0..input[1]) {
            for (j in 0 downTo input[2]) {
                simulate(i, j, input).let { (hit, _) ->
                    if (hit) {
                        count++
                        seen.add(i to j)
                    }
                }
            }
        }
        return count + discoverLandingPoints(input, seen).second
    }
}

fun main() {
    println(Exercise17.part1())
    println(Exercise17.part2())
}
