package com.ncorti.aoc2021

object Exercise09 {

    private fun getInput(): List<List<Int>> =
        getInputAsTest("09") { split("\n") }.map { line ->
            line.toCharArray().map { it.digitToInt() }
        }

    private fun List<List<Int>>.isALowerPoint(i: Int, j: Int): Boolean =
        (i == 0 || this[i][j] < this[i - 1][j]) &&
            (j == 0 || this[i][j] < this[i][j - 1]) &&
            (i == this.size - 1 || this[i][j] < this[i + 1][j]) &&
            (j == this[i].size - 1 || this[i][j] < this[i][j + 1])

    private fun discoverBasin(world: List<List<Int>>, starti: Int, startj: Int): Int {
        val seen = mutableSetOf<Pair<Int, Int>>()
        val next = mutableListOf(starti to startj)
        while (next.isNotEmpty()) {
            val (i, j) = next.removeFirst()
            seen.add(i to j)
            if (i != 0 && world[i - 1][j] != 9 && (i - 1 to j) !in seen) {
                next.add(i - 1 to j)
            }
            if (j != 0 && world[i][j - 1] != 9 && (i to j - 1) !in seen) {
                next.add(i to j - 1)
            }
            if (i != world.size - 1 && world[i + 1][j] != 9 && (i + 1 to j) !in seen) {
                next.add(i + 1 to j)
            }
            if (j != world[0].size - 1 && world[i][j + 1] != 9 && (i to j + 1) !in seen) {
                next.add(i to j + 1)
            }
        }
        return seen.size
    }

    fun part1(): Int =
        getInput().let { world ->
            world
                .mapIndexed { i, line ->
                    line.filterIndexed { j, _ -> world.isALowerPoint(i, j) }.sumOf { it + 1 }
                }
                .sum()
        }

    fun part2(): Int =
        getInput()
            .let { world ->
                world.flatMapIndexed { i, line ->
                    List(line.size) { j ->
                        if (world.isALowerPoint(i, j)) {
                            discoverBasin(world, i, j)
                        } else {
                            0
                        }
                    }
                }
            }
            .sortedDescending()
            .take(3)
            .reduce { acc, next -> acc * next }
}

fun main() {
    println(Exercise09.part1())
    println(Exercise09.part2())
}
