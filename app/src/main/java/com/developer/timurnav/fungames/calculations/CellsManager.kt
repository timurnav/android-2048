package com.developer.timurnav.fungames.calculations

import com.developer.timurnav.fungames.domain.TileTo
import java.util.*

class CellsManager(private val lastIndex: Int) {

    private var nextId = 0

    private val random = Random()

    fun createAtBottom(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[lastIndex][it] },
                consume = { index, value ->
                    newData[lastIndex][index] = value
                }
        )
        return newData
    }


    fun createAtTop(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[0][it] },
                consume = { index, value ->
                    newData[0][index] = value
                }
        )
        return newData
    }

    fun createAtRight(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[it][lastIndex] },
                consume = { index, value ->
                    newData[index][lastIndex] = value
                }
        )
        return newData
    }

    fun createAtLeft(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = allocateNewData(data)
        doCreate(
                supply = { data[it][0] },
                consume = { index, value ->
                    newData[index][0] = value
                }
        )
        return newData
    }

    private fun doCreate(supply: (Int) -> TileTo?, consume: (Int, TileTo) -> Unit) {
        val availableIndexes = (0..lastIndex)
                .filter { supply(it) == null }
                .shuffled()
        consume(availableIndexes[0], TileTo(nextId++, if (random.nextInt(100) > 80) 4 else 2))
    }

    private fun allocateNewData(data: List<List<TileTo?>>): MutableList<MutableList<TileTo?>> {
        return MutableList(data.size, { row ->
            MutableList(data.size, { column ->
                data[row][column]
            })
        })
    }
}
