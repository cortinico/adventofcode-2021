package com.ncorti.aoc2021

object Exercise14 {

    private fun getInput(): Pair<Map<String, Char>, MutableMap<String, Long>> {
        val input = getInputAsTest("14") { split("\n") }
        val rules =
            input.drop(2).map { it.split(" -> ") }.associate { (key, value) ->
                key to value.toCharArray().first()
            }
        val counts = rules.map { (key, _) -> key to 0L }.toMap().toMutableMap()
        input.first().windowed(2).forEach { counts[it] = counts.getOrDefault(it, 0L) + 1 }
        return rules to counts
    }

    private fun runPolymerization(
        rules: Map<String, Char>,
        initialCounts: MutableMap<String, Long>,
        steps: Int
    ): Long {
        var counts = initialCounts
        repeat(steps) {
            val newCounts = mutableMapOf<String, Long>()
            counts.forEach { (pair, count) ->
                if (pair in rules && count != 0L) {
                    val char = rules[pair]!!
                    val firstPair = pair.toCharArray()[0] + char.toString()
                    newCounts[firstPair] = newCounts.getOrDefault(firstPair, 0) + count
                    val secondPair = char + pair.toCharArray()[1].toString()
                    newCounts[secondPair] = newCounts.getOrDefault(secondPair, 0) + count
                }
            }
            counts = newCounts
        }
        val alphabet =
            counts
                .map { (pair, count) -> pair.toCharArray()[1] to count }
                .groupingBy { it.first }
                .aggregate { _, acc: Long?, (_, count), _ -> (acc ?: 0L) + count }

        return alphabet.maxOf { it.value } - alphabet.minOf { it.value }
    }

    fun part1() = getInput().let { (rules, counts) -> runPolymerization(rules, counts, 10) }

    fun part2() = getInput().let { (rules, counts) -> runPolymerization(rules, counts, 40) }
}

fun main() {
    println(Exercise14.part1())
    println(Exercise14.part2())
}
