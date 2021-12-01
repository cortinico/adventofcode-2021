package com.ncorti.aoc2021

internal fun <R> getInputAsTest(day: String, transform: String.() -> R): R =
    object {}.javaClass.getResource("input-$day.txt")?.readText()?.let { it.transform() }
        ?: error("Failed to read day $day input")
