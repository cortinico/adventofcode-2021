package com.ncorti.aoc2021

import java.util.Stack

object Exercise10 {

    fun part1() =
        getInputAsTest("10") { split("\n") }
            .map {
                val stack = Stack<Char>()
                val chars = it.toCharArray().toMutableList()
                stack.push(chars.removeFirst())
                while (chars.isNotEmpty() && stack.isNotEmpty()) {
                    when (val next = chars.removeFirst()) {
                        '<', '(', '[', '{' -> stack.push(next)
                        '>' -> if (stack.peek() == '<') stack.pop() else return@map 25137
                        ')' -> if (stack.peek() == '(') stack.pop() else return@map 3
                        ']' -> if (stack.peek() == '[') stack.pop() else return@map 57
                        '}' -> if (stack.peek() == '{') stack.pop() else return@map 1197
                    }
                }
                0
            }
            .sum()

    fun part2(): Long {
        val scores =
            getInputAsTest("10") { split("\n") }
                .map {
                    val stack = Stack<Char>()
                    val chars = it.toCharArray().toMutableList()
                    stack.push(chars.removeFirst())
                    while (chars.isNotEmpty()) {
                        when (val next = chars.removeFirst()) {
                            '<', '(', '[', '{' -> stack.push(next)
                            '>' -> if (stack.peek() == '<') stack.pop() else return@map Stack()
                            ')' -> if (stack.peek() == '(') stack.pop() else return@map Stack()
                            ']' -> if (stack.peek() == '[') stack.pop() else return@map Stack()
                            '}' -> if (stack.peek() == '{') stack.pop() else return@map Stack()
                        }
                    }
                    stack
                }
                .filter { it.isNotEmpty() }
                .map {
                    var tempScore = 0L
                    while (it.isNotEmpty()) {
                        tempScore *= 5L
                        tempScore +=
                            when (it.pop()) {
                                '(' -> 1L
                                '[' -> 2L
                                '{' -> 3L
                                else -> 4L
                            }
                    }
                    tempScore
                }
                .sorted()
        return scores[(scores.size / 2)]
    }
}

fun main() {
    println(Exercise10.part1())
    println(Exercise10.part2())
}
