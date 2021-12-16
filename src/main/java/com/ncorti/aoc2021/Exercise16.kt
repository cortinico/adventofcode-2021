package com.ncorti.aoc2021

object Exercise16 {

    private fun getInput() =
        getInputAsTest("16") {
            toCharArray().joinToString("") {
                when (it) {
                    '0' -> "0000"
                    '1' -> "0001"
                    '2' -> "0010"
                    '3' -> "0011"
                    '4' -> "0100"
                    '5' -> "0101"
                    '6' -> "0110"
                    '7' -> "0111"
                    '8' -> "1000"
                    '9' -> "1001"
                    'A' -> "1010"
                    'B' -> "1011"
                    'C' -> "1100"
                    'D' -> "1101"
                    'E' -> "1110"
                    else -> "1111"
                }
            }
        }

    private fun parsePacket(chars: CharArray, i: Int = 0): Triple<Int, Int, Long> {
        val version = (i..i + 2).map { chars[it] }.joinToString("").toInt(2)
        val typeId = (i + 3..i + 5).map { chars[it] }.joinToString("").toInt(2)
        val (nexti, partialVersion, value) =
            if (typeId == 4) {
                parsePacketLiteral(chars, i + 6)
            } else {
                parsePacketOperator(chars, i + 6, typeId)
            }
        return Triple(nexti, version + partialVersion, value)
    }

    private fun parsePacketLiteral(chars: CharArray, i: Int): Triple<Int, Int, Long> {
        var nexti = i
        val number = mutableListOf<Char>()
        while (chars[nexti] == '1') {
            (1..4).onEach { number.add(chars[nexti + it]) }
            nexti += 5
        }
        (1..4).onEach { number.add(chars[nexti + it]) }
        nexti += 5
        return Triple(nexti, 0, number.joinToString("").toLong(2))
    }

    private fun parsePacketOperator(
        chars: CharArray,
        starti: Int,
        typeId: Int
    ): Triple<Int, Int, Long> {
        var i = starti
        var sum = 0
        val partialResults = mutableListOf<Long>()
        if (chars[i] == '0') {
            val length = (i + 1..i + 15).map { chars[it] }.joinToString("").toInt(2)
            i += 16
            val target = i + length
            while (i < target) {
                val (nexti, partialVersion, partialResult) = parsePacket(chars, i)
                i = nexti
                sum += partialVersion
                partialResults += partialResult
            }
        } else {
            val packets = (i + 1..i + 11).map { chars[it] }.joinToString("").toInt(2)
            i += 12
            repeat(packets) {
                val (nexti, partialVersion, partialResult) = parsePacket(chars, i)
                i = nexti
                sum += partialVersion
                partialResults += partialResult
            }
        }
        val computation =
            when (typeId) {
                0 -> partialResults.sum()
                1 -> partialResults.fold(1L) { next, acc -> next * acc }
                2 -> partialResults.minOrNull()!!
                3 -> partialResults.maxOrNull()!!
                5 -> if (partialResults[0] > partialResults[1]) 1L else 0L
                6 -> if (partialResults[0] < partialResults[1]) 1L else 0L
                else -> if (partialResults[0] == partialResults[1]) 1L else 0L
            }
        return Triple(i, sum, computation)
    }

    fun part1() = parsePacket(getInput().toCharArray(), 0).second

    fun part2() = parsePacket(getInput().toCharArray(), 0).third
}

fun main() {
    println(Exercise16.part1())
    println(Exercise16.part2())
}
