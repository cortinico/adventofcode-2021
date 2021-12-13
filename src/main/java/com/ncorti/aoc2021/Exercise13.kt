package com.ncorti.aoc2021

object Exercise13 {

    private fun getInput(): Pair<List<String>, Array<IntArray>> {
        val input = getInputAsTest("13") { split("\n") }
        val size =
            input.filter { "," in it }.flatMap { it.trim().split(",") }.maxOf(String::toInt) + 1

        val world = Array(size) { i -> IntArray(size) { j -> if ("$j,$i" in input) 1 else 0 } }
        return input to world
    }

    private fun Array<IntArray>.runFolding(folds: List<List<String>>) =
        folds.forEach { (axes, number) ->
            if (axes == "y") {
                val row = number.toInt()
                for (i in 1 until this.size - row) {
                    for (j in this.indices) {
                        if (row - i >= 0) {
                            this[row - i][j] += this[row + i][j]
                            this[row + i][j] = 0
                        }
                    }
                }
            } else {
                val col = number.toInt()
                for (i in 1 until this.size - col) {
                    for (j in this.indices) {
                        if (col - i >= 0) {
                            this[j][col - i] += this[j][col + i]
                            this[j][col + i] = 0
                        }
                    }
                }
            }
        }

    fun part1() =
        getInput().let { (input, world) ->
            world.runFolding(listOf(input.first { "fold" in it }.split(" ").last().split("=")))
            return@let world.sumOf { it.count { cell -> cell != 0 } }
        }

    fun part2() =
        getInput().let { (input, world) ->
            world.runFolding(input.filter { "fold" in it }.map { it.split(" ").last().split("=") })
            val lastX = input.last { "x" in it }.split("=").last().toInt()
            val lastY = input.last { "y" in it }.split("=").last().toInt()
            for (i in 0..lastY) {
                for (j in 0..lastX) {
                    print(if (world[i][j] == 0) "." else "#")
                    print("\t")
                }
                println("")
            }
            // Answer is on the stdout and here for your convenience ¯\_(ツ)_/¯
            return@let "CJCKBAPB"
        }
}

fun main() {
    println(Exercise13.part1())
    println(Exercise13.part2())
}
