package com.ncorti.aoc2021

internal fun getInputAsTest(day: String): String =
    object {}.javaClass.getResource("input-$day.txt")?.readText()
        ?: error("Failed to read day $day input")
