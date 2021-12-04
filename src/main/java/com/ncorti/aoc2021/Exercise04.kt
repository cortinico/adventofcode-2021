package com.ncorti.aoc2021

object Exercise04 {

    private fun getInput(): Pair<List<Int>, List<Array<IntArray>>> =
        getInputAsTest("04") { split("\n") }.let { lines ->
            val calls = lines[0].split(",").map(String::toInt)
            val boards =
                lines
                    .asSequence()
                    .drop(2)
                    .map(String::trim)
                    .filter(String::isNotBlank)
                    .withIndex()
                    .groupBy { line -> line.index / 5 }
                    .map { (_, lines) ->
                        Array(5) { i ->
                            val line = lines[i].value.split(" ").filter(String::isNotBlank)
                            IntArray(5) { j -> line[j].toInt() }
                        }
                    }
                    .toList()
            calls to boards
        }

    private fun checkBingo(board: Array<IntArray>, row: Int, col: Int): Boolean =
        board[row].all { it == -1 } || board.map { it[col] }.all { it == -1 }

    fun part1(): Int {
        val (calls, boards) = getInput()
        calls.forEach { call ->
            boards.forEach { board ->
                val row: Int = board.indexOfFirst { row -> call in row }
                val col: Int = if (row == -1) -1 else board[row].indexOfFirst { it == call }
                if (row != -1 && col != -1) {
                    board[row][col] = -1
                    if (checkBingo(board, row, col)) {
                        return call * board.sumOf { it.filter { it != -1 }.sum() }
                    }
                }
            }
        }
        error("Bingo never happened!")
    }

    fun part2(): Int {
        val (calls, boardsArray) = getInput()
        val boards = boardsArray.toMutableList()
        calls.forEach { call ->
            val boardsToRemove = mutableSetOf<Array<IntArray>>()
            for (i in boards.indices) {
                val board = boards[i]
                val row: Int = board.indexOfFirst { row -> call in row }
                val col: Int = if (row == -1) -1 else board[row].indexOfFirst { it == call }
                if (row != -1 && col != -1) {
                    board[row][col] = -1
                    if (checkBingo(board, row, col)) {
                        if (boards.size == 1) {
                            return call * board.sumOf { it.filter { it != -1 }.sum() }
                        } else {
                            boardsToRemove.add(board)
                        }
                    }
                }
            }
            boardsToRemove.forEach { boards.remove(it) }
        }
        error("Last board not found!")
    }
}

fun main() {
    println(Exercise04.part1())
    println(Exercise04.part2())
}
