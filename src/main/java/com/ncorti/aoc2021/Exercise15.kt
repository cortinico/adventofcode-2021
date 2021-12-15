package com.ncorti.aoc2021

import java.util.PriorityQueue

object Exercise15 {

    private fun getInput() =
        getInputAsTest("15") { split("\n") }
            .map { line -> line.toCharArray().map { it.digitToInt() }.toTypedArray() }
            .toTypedArray()

    // Not the most elegant solution but it does the job :)
    private fun getBiggerInput() =
        getInput().let {
            Array(it.size * 5) { i ->
                Array(it.size * 5) { j ->
                    val modi = i % it.size
                    val divi = i / it.size
                    val modj = j % it.size
                    val divj = j / it.size
                    val partial = it[modi][modj] + divi + divj
                    partial % 10 + partial / 10
                }
            }
        }

    private fun findShortestPath(world: Array<Array<Int>>): Int {
        val queue =
            PriorityQueue<Triple<Int, Int, Int>> { (_, _, cost1), (_, _, cost2) -> cost1 - cost2 }
        val dist =
            Array(world.size) { i ->
                IntArray(world[0].size) { j ->
                    if (i == 0 && j == 0) {
                        queue.add(Triple(i, j, 0))
                        0
                    } else {
                        queue.add(Triple(i, j, Int.MAX_VALUE))
                        Int.MAX_VALUE
                    }
                }
            }

        while (queue.isNotEmpty()) {
            val (curri, currj, currd) = queue.remove()
            steps.forEach { (stepi, stepj) ->
                val (newi, newj) = curri + stepi to currj + stepj
                if (newi >= 0 && newj >= 0 && newi < world.size && newj < world.size) {
                    val newDist = currd + world[curri + stepi][currj + stepj]
                    if (newDist < dist[newi][newj]) {
                        dist[newi][newj] = newDist
                        queue.removeIf { (i, j, _) -> i == newi && j == newj }
                        queue.add(Triple(newi, newj, newDist))
                        if (newi == world.size - 1 && newj == world.size - 1) {
                            return newDist
                        }
                    }
                }
            }
        }
        return dist[world.size - 1][world.size - 1]
    }

    private val steps = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)

    fun part1() = findShortestPath(getInput())

    fun part2() = findShortestPath(getBiggerInput())
}

fun main() {
    println(Exercise15.part1())
    println(Exercise15.part2())
}
