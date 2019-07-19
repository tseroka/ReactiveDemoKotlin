package com.example.reactive.kotlin.movie

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class MovieServiceIntegrationTest {

    @Autowired
    private lateinit var movieService: MovieService

    @Test
    fun da() {
        movieService.saveMovie(MovieCommand(
                "dsa","asd","2018-01-01", "Action"
        ))

        val movie = movieService.findByTitle("dsa").blockFirst()!!
        println(movie.rate.totalRating)
        movieService.addRate(movie.id, 9)

        val movie2 = movieService.findByTitle("dsa").blockFirst()!!
        println(movie2.rate.totalRating)
    }
}