package com.example.reactive.kotlin.movie

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieTest {

    @Test
    fun `test rating`() {
        val rating = Rate()
        rating.addRate(3)
        rating.addRate(5)
        assertEquals(4.0, rating.totalRating, 0.1)
        assertEquals(2, rating.ratingCount)
    }
}