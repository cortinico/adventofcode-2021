package com.ncorti.aoc2021

object Exercise08 {

    fun part1(): Int =
        getInputAsTest("08") { split("\n") }
            .map { it.substring(it.indexOf(" | ") + 1) }
            .map(String::trim)
            .flatMap { it.split(" ") }
            .count { it.length in setOf(2, 3, 4, 7) }

    fun part2(): Int =
        getInputAsTest("08") { split("\n") }
            .map { it.split(" | ").let { tokens -> tokens[0] to tokens[1] } }
            .sumOf { (template, code) ->
                val combo = Array(7) { "" }
                val patterns = template.split(" ")
                patterns.filter { it.length == 2 }.forEach {
                    combo[2] = it
                    combo[5] = it
                }
                patterns.filter { it.length == 3 }.forEach {
                    combo[0] = it.toCharArray().first { char -> char !in combo[2] }.toString()
                }
                patterns.filter { it.length == 4 }.forEach {
                    val char4 =
                        it.toCharArray().filter { char -> char !in combo[2] }.joinToString("")
                    combo[1] = char4
                    combo[3] = char4
                }
                patterns.filter { it.length == 5 }.forEach {
                    val char5 =
                        it.toCharArray()
                            .filter { char ->
                                char !in combo[0] && char !in combo[1] && char !in combo[2]
                            }
                            .joinToString("")
                    if (char5.length == 1) {
                        combo[6] = char5
                    } else {
                        combo[4] = char5
                    }
                }
                combo[4] = combo[4].toCharArray().filter { it !in combo[6] }.joinToString("")
                val intersect6 =
                    patterns
                        .filter { it.length == 6 }
                        .map { it.toCharArray().toSet() }
                        .reduce { curr, next -> curr intersect next }
                        .filter { it !in combo[0] && it !in combo[6] }

                val comb5 = intersect6.first { it in combo[5] }
                combo[5] = comb5.toString()
                combo[2] = combo[2].toCharArray().filter { it !in combo[5] }.joinToString("")
                combo[1] = intersect6.first { it != comb5 }.toString()

                combo[3] =
                    patterns
                        .first { it.length == 7 }
                        .toCharArray()
                        .first {
                            it !in combo[0] &&
                                it !in combo[1] &&
                                it !in combo[2] &&
                                it !in combo[4] &&
                                it !in combo[5] &&
                                it !in combo[6]
                        }
                        .toString()

                code.trim()
                    .split(" ")
                    .joinToString("") {
                        when (it.length) {
                            7 -> "8"
                            3 -> "7"
                            2 -> "1"
                            4 -> "4"
                            6 -> if (combo[3] !in it) "0" else if (combo[2] !in it) "6" else "9"
                            else -> if (combo[1] in it) "5" else if (combo[4] in it) "2" else "3"
                        }
                    }
                    .toInt()
            }
}

fun main() {
    println(Exercise08.part1())
    println(Exercise08.part2())
}
