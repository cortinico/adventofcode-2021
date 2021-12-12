package com.ncorti.aoc2021

import java.util.Stack

object Exercise12 {

    private fun getInput(): HashMap<String, MutableList<String>> {
        val input =
            getInputAsTest("12") { split("\n") }.map { line ->
                line.split("-").let { it[0] to it[1] }
            }
        val world =
            hashMapOf(
                *input
                    .flatMap { listOf(it.first, it.second) }
                    .distinct()
                    .map { it to mutableListOf<String>() }
                    .toTypedArray())
        input.forEach { (start, end) ->
            world[start]?.add(end)
            world[end]?.add(start)
        }
        return world
    }

    private fun visit(
        world: HashMap<String, MutableList<String>>,
        visitCriteria: (String, List<String>) -> Boolean
    ): List<String> {
        val next = Stack<List<String>>().apply { push(listOf("start")) }
        val visit = mutableListOf<String>()
        while (next.isNotEmpty()) {
            val curr = next.pop()
            val edge = curr.last()
            if (edge == "end") {
                visit.add(curr.joinToString(","))
            } else {
                world[edge]?.forEach {
                    if (visitCriteria(it, curr) || it == "end") {
                        next.add(curr + it)
                    }
                }
            }
        }
        return visit
    }

    private fun List<String>.hasASmallCave(): Boolean = this.count { it.isASmallCave() } >= 1

    private fun List<String>.hasDuplicatedSmallCave(): Boolean =
        this.count { it.isASmallCave() } != this.distinct().count { it.isASmallCave() }

    private fun String.isASmallCave(): Boolean =
        this != "start" && this != "end" && this.uppercase() != this

    private fun String.isABigCave(): Boolean =
        this != "start" && this != "end" && this.uppercase() == this

    fun part1(): Int =
        visit(getInput()) { cave, currentSeen ->
                (cave.isASmallCave() && cave !in currentSeen) ||
                    (cave.isASmallCave() && !currentSeen.hasASmallCave()) ||
                    cave.isABigCave()
            }
            .size

    fun part2(): Int =
        visit(getInput()) { cave, currentSeen ->
                (cave.isASmallCave() && cave !in currentSeen) ||
                    (cave.isASmallCave() && !currentSeen.hasDuplicatedSmallCave()) ||
                    cave.isABigCave()
            }
            .size
}

fun main() {
    println(Exercise12.part1())
    println(Exercise12.part2())
}
