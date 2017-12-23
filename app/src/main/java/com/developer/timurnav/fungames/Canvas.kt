package com.developer.timurnav.fungames

import com.developer.timurnav.fungames.Direction.*
import java.util.*
import kotlin.collections.ArrayList

class Canvas(size: Int) {

    private val lastIndex = size - 1
    private var data = List(size, { _ -> List(size, { _ -> 0 }) })

    private val movingManager = MovingManager(lastIndex)
    private val cellsManager = CellsManager(lastIndex)

    init {
        val directions = Direction.values().toList().shuffled()
        createNewNumber(directions[0])
        createNewNumber(directions[1])
    }

    fun isGameOver(): Boolean {
        return !(0..lastIndex).any { row ->
            (0..lastIndex).any { column ->
                data[row][column] == 0 ||
                        (row != lastIndex && data[row][column] == data[row + 1][column]) ||
                        (column != lastIndex && data[row][column] == data[row][column + 1])
            }
        }
    }

    fun move(direction: Direction): Boolean {
        val updated = when (direction) {
            UP -> movingManager.up(data)
            DOWN -> movingManager.down(data)
            LEFT -> movingManager.left(data)
            RIGHT -> movingManager.right(data)
        }
        val moved = updated != data
        data = updated
        return moved
    }

    fun createNewNumber(lastMove: Direction) {
        data = when (lastMove) {
            UP -> cellsManager.createAtBottom(data)
            DOWN -> cellsManager.createAtTop(data)
            LEFT -> cellsManager.createAtRight(data)
            RIGHT -> cellsManager.createAtLeft(data)
        }
    }

    fun numberAt(column: Int, row: Int): Int {
        return data[row][column]
    }
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class MovingManager(private val lastIndex: Int) {

    fun up(data: List<List<Int>>): List<List<Int>> {

        val newData = MutableList(data.size, { _ -> MutableList(data.size, { _ -> 0 }) })
        doCalculate(
                supplyZeroless = { column ->
                    (0..lastIndex)
                            .map { data[it][column] }
                            .filter { it != 0 }
                },
                applyChanges = { cleared, column ->
                    (0..lastIndex)
                            .map {
                                newData[it][column] =
                                        if (it < cleared.size)
                                            cleared[it]
                                        else 0
                            }
                }
        )
        return newData
    }

    fun down(data: List<List<Int>>): List<List<Int>> {
        val newData = MutableList(data.size, { _ -> MutableList(data.size, { _ -> 0 }) })
        doCalculate(
                supplyZeroless = { column ->
                    (0..lastIndex)
                            .map { data[it][column] }
                            .filter { it != 0 }
                            .reversed()
                },
                applyChanges = { cleared, column ->
                    (lastIndex downTo 0)
                            .map {
                                newData[it][column] =
                                        if (lastIndex - it < cleared.size)
                                            cleared[lastIndex - it]
                                        else 0
                            }
                })
        return newData
    }

    fun left(data: List<List<Int>>): List<List<Int>> {
        val newData = MutableList(data.size, { _ -> MutableList(data.size, { _ -> 0 }) })
        doCalculate(
                supplyZeroless = { row ->
                    (0..lastIndex)
                            .map { data[row][it] }
                            .filter { it != 0 }
                },
                applyChanges = { cleared, row ->
                    (0..lastIndex)
                            .forEach {
                                newData[row][it] =
                                        if (it < cleared.size)
                                            cleared[it]
                                        else 0
                            }
                })
        return newData
    }

    fun right(data: List<List<Int>>): List<List<Int>> {
        val newData = MutableList(data.size, { _ -> MutableList(data.size, { _ -> 0 }) })
        doCalculate(
                supplyZeroless = { row ->
                    (0..lastIndex)
                            .map { data[row][it] }
                            .filter { it != 0 }
                            .reversed()
                },
                applyChanges = { cleared, row ->
                    (lastIndex downTo 0)
                            .forEach {
                                newData[row][it] =
                                        if (lastIndex - it < cleared.size)
                                            cleared[lastIndex - it]
                                        else 0
                            }
                })
        return newData
    }

    private fun doCalculate(
            supplyZeroless: (Int) -> List<Int>,
            applyChanges: (List<Int>, Int) -> Unit
    ) {
        (0..lastIndex).forEach { iter ->
            val zeroless = supplyZeroless(iter)

            if (zeroless.isEmpty()) return@forEach

            val cleared = ArrayList<Int>()
            var index = 0
            while (index < zeroless.size) {
                if (index != zeroless.size - 1 && zeroless[index] == zeroless[index + 1]) {
                    cleared.add(zeroless[index] shl 1)
                    index++
                } else {
                    cleared.add(zeroless[index])
                }
                index++
            }

            applyChanges(cleared, iter)
        }
    }

}

class CellsManager(private val lastIndex: Int) {

    private val random = Random()

    fun createAtBottom(data: List<List<Int>>): List<List<Int>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[lastIndex][it] },
                consume = { index, value ->
                    newData[lastIndex][index] = value
                }
        )
        return newData
    }


    fun createAtTop(data: List<List<Int>>): List<List<Int>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[0][it] },
                consume = { index, value ->
                    newData[0][index] = value
                }
        )
        return newData
    }

    fun createAtRight(data: List<List<Int>>): List<List<Int>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[it][lastIndex] },
                consume = { index, value ->
                    newData[index][lastIndex] = value
                }
        )
        return newData
    }

    fun createAtLeft(data: List<List<Int>>): List<List<Int>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[it][0] },
                consume = { index, value ->
                    newData[index][0] = value
                }
        )
        return newData
    }

    private fun doCreate(supply: (Int) -> Int, consume: (Int, Int) -> Unit) {
        val availableIndexes = (0..lastIndex)
                .filter { supply(it) == 0 }
                .shuffled()
        consume(availableIndexes[0], if (random.nextInt(100) > 80) 4 else 2)
    }

    private fun allocateNewData(data: List<List<Int>>): MutableList<MutableList<Int>> {
        return MutableList(data.size, { row ->
            MutableList(data.size, { column ->
                data[row][column]
            })
        })
    }

}