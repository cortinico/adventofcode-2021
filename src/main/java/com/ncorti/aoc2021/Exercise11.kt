package com.ncorti.aoc2021

object Exercise11 {

    private fun getInput() =
        getInputAsTest("11") { split("\n") }
            .map { line -> line.toCharArray().map { it.digitToInt() }.toTypedArray() }
            .toTypedArray()

    private fun Array<Array<Int>>.letItFlash(): Int {
        var count = 0
        for (i in this.indices) {
            for (j in this.indices) {
                if (this[i][j] == -1) {
                    count++
                    this[i][j] = 0
                }
            }
        }
        return count
    }

    private val steps = listOf(-1 to 0, -1 to -1, 0 to -1, 1 to 0, 1 to 1, 0 to 1, 1 to -1, -1 to 1)

    private fun Array<Array<Int>>.findFlashCandidates(): MutableSet<Pair<Int, Int>> {
        val toFlash = mutableSetOf<Pair<Int, Int>>()
        for (i in this.indices) {
            for (j in this.indices) {
                this[i][j] += 1
                if (this[i][j] > 9) {
                    toFlash.add(i to j)
                }
            }
        }
        return toFlash
    }

    private fun runSimulation(times: Int): Pair<Int, Int> {
        val world = getInput()

        var flashes = 0
        val toFlash = mutableListOf<Pair<Int, Int>>()
        repeat(times) { time ->
            toFlash.addAll(world.findFlashCandidates())
            while (toFlash.isNotEmpty()) {
                val (i, j) = toFlash.removeFirst()
                world[i][j] = -1
                flashes++
                steps.forEach { (modi, modj) ->
                    val (newi, newj) = i + modi to j + modj
                    if (newi in world.indices &&
                        newj in world.indices &&
                        world[newi][newj] != -1 &&
                        (newi to newj) !in toFlash) {
                        world[newi][newj] += 1
                        if (world[newi][newj] > 9) {
                            toFlash.add(newi to newj)
                        }
                    }
                }
            }
            val count = world.letItFlash()
            if (count == world.size * world.size) {
                return (flashes to time + 1)
            }
        }
        return (flashes to times)
    }

    fun part1(): Int = runSimulation(100).first

    fun part2(): Int = runSimulation(Int.MAX_VALUE).second
}

fun main() {
    println(Exercise11.part1())
    println(Exercise11.part2())
}
