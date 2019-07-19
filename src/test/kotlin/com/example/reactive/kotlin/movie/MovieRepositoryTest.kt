package com.example.reactive.kotlin.movie

import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier

@RunWith(SpringRunner::class)
@SpringBootTest
class MoviesRepositoryIntegrationTest {

    @Autowired
    private lateinit var movieRepository: MovieRepository

    @Test
    fun `get movies by title test`() {
        // given
        val otherMovie = movie(title = "Other")
        saveMovies(movie(title = "More"), movie(title = "Seven"), otherMovie)

        // when
        val movies = movieRepository.findByTitle("Other")

        // then
        StepVerifier
        .create(movies)
        .expectNext(otherMovie)
        .expectComplete()
        .verify()
    }

    private fun saveMovies(vararg movie: Movie) {
        movie.forEach {
            movieRepository.save(it).subscribe()
        }
    }

    @After
    fun tearDown() {
        movieRepository.deleteAll()
    }
}