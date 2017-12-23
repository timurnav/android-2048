package com.developer.timurnav.fungames

import org.junit.Assert.assertEquals
import org.junit.Test

class MovingManagerTest {

    @Test
    fun testUpMoving() {

        val data = listOf(
                listOf(0, 2, 0, 0, 2),
                listOf(0, 4, 4, 4, 2),
                listOf(0, 8, 0, 4, 0),
                listOf(0, 4, 0, 4, 2),
                listOf(2, 2, 4, 0, 2))

        val expected = listOf(
                listOf(2, 2, 8, 8, 4),
                listOf(0, 4, 0, 4, 4),
                listOf(0, 8, 0, 0, 0),
                listOf(0, 4, 0, 0, 0),
                listOf(0, 2, 0, 0, 0))
        val result = MovingManager(data.size - 1).up(data)
        doCheck(result, expected)
    }

    @Test
    fun testDownMoving() {

        val data = listOf(
                listOf(2, 2, 0, 0, 2),
                listOf(0, 4, 4, 4, 2),
                listOf(0, 8, 0, 4, 0),
                listOf(0, 4, 0, 4, 2),
                listOf(0, 2, 4, 0, 2))

        val expected = listOf(
                listOf(0, 2, 0, 0, 0),
                listOf(0, 4, 0, 0, 0),
                listOf(0, 8, 0, 0, 0),
                listOf(0, 4, 0, 4, 4),
                listOf(2, 2, 8, 8, 4))
        val result = MovingManager(data.size - 1).down(data)
        doCheck(result, expected)
    }

    @Test
    fun testLeftMoving() {

        val data = listOf(
                listOf(0, 0, 0, 0, 2),
                listOf(2, 4, 8, 4, 2),
                listOf(4, 0, 0, 0, 4),
                listOf(4, 4, 4, 0, 0),
                listOf(2, 0, 2, 2, 2))

        val expected = listOf(
                listOf(2, 0, 0, 0, 0),
                listOf(2, 4, 8, 4, 2),
                listOf(8, 0, 0, 0, 0),
                listOf(8, 4, 0, 0, 0),
                listOf(4, 4, 0, 0, 0))
        val result = MovingManager(data.size - 1).left(data)
        doCheck(result, expected)
    }

    @Test
    fun testRightMoving() {

        val data = listOf(
                listOf(2, 0, 0, 0, 0),
                listOf(2, 4, 8, 4, 2),
                listOf(4, 0, 0, 0, 4),
                listOf(4, 4, 4, 0, 0),
                listOf(2, 0, 2, 2, 2))

        val expected = listOf(
                listOf(0, 0, 0, 0, 2),
                listOf(2, 4, 8, 4, 2),
                listOf(0, 0, 0, 0, 8),
                listOf(0, 0, 0, 4, 8),
                listOf(0, 0, 0, 4, 4))

        val result = MovingManager(data.size - 1).right(data)
        doCheck(result, expected)
    }

    private fun doCheck(result: List<List<Int>>, expected: List<List<Int>>) {

        for (row in result.indices) {
            assertEquals(
                    String.format(
                            "Expected: %s, But Actual %s",
                            expected[row], result[row]),
                    expected[row], result[row])
        }
    }

}