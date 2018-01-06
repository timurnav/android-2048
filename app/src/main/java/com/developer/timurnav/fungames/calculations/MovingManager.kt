package com.developer.timurnav.fungames.calculations

import com.developer.timurnav.fungames.domain.TileTo

class MovingManager(private val lastIndex: Int) {

    fun up(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = MutableList(data.size, { _ -> MutableList<TileTo?>(data.size, { _ -> null }) })
        doCalculate(
                supplyZeroless = { column ->
                    (0..lastIndex).mapNotNull { data[it][column] }
                },
                applyChanges = { cleared, column ->
                    (0..lastIndex)
                            .map {
                                newData[it][column] =
                                        if (it < cleared.size)
                                            cleared[it]
                                        else null
                            }
                }
        )
        return newData
    }

    fun down(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = MutableList(data.size, { _ -> MutableList<TileTo?>(data.size, { _ -> null }) })
        doCalculate(
                supplyZeroless = { column ->
                    (0..lastIndex)
                            .mapNotNull { data[it][column] }
                            .reversed()
                },
                applyChanges = { cleared, column ->
                    (lastIndex downTo 0)
                            .map {
                                newData[it][column] =
                                        if (lastIndex - it < cleared.size)
                                            cleared[lastIndex - it]
                                        else null
                            }
                })
        return newData
    }

    fun left(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = MutableList(data.size, { _ -> MutableList<TileTo?>(data.size, { _ -> null }) })
        doCalculate(
                supplyZeroless = { row ->
                    (0..lastIndex)
                            .mapNotNull { data[row][it] }
                },
                applyChanges = { cleared, row ->
                    (0..lastIndex)
                            .forEach {
                                newData[row][it] =
                                        if (it < cleared.size)
                                            cleared[it]
                                        else null
                            }
                })
        return newData
    }

    fun right(data: List<List<TileTo?>>): List<List<TileTo?>> {
        val newData = MutableList(data.size, { _ -> MutableList<TileTo?>(data.size, { _ -> null }) })
        doCalculate(
                supplyZeroless = { row ->
                    (0..lastIndex)
                            .mapNotNull { data[row][it] }
                            .reversed()
                },
                applyChanges = { cleared, row ->
                    (lastIndex downTo 0)
                            .forEach {
                                newData[row][it] =
                                        if (lastIndex - it < cleared.size)
                                            cleared[lastIndex - it]
                                        else null
                            }
                })
        return newData
    }

    private fun doCalculate(
            supplyZeroless: (Int) -> List<TileTo>,
            applyChanges: (List<TileTo>, Int) -> Unit
    ) {
        (0..lastIndex).forEach { iter ->
            val zeroless = supplyZeroless(iter)

            if (zeroless.isEmpty()) return@forEach

            val cleared = ArrayList<TileTo>()
            var index = 0
            while (index < zeroless.size) {
                if (index != zeroless.size - 1 && zeroless[index].value() == zeroless[index + 1].value()) {
                    val tileTo = zeroless[index]
                    tileTo.upgrade(zeroless[++index].id)
                    cleared.add(tileTo)
                } else {
                    cleared.add(zeroless[index])
                }
                index++
            }

            applyChanges(cleared, iter)
        }
    }

}
