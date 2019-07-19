package com.example.reactive.kotlin.movie

import com.example.reactive.kotlin.ReactiveDemoKotlinApplication
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@SpringBootTest(classes = [ReactiveDemoKotlinApplication::class])
@RunWith(SpringRunner::class)
class MovieEndpointTest {

    @Autowired
    private lateinit var movieEndpoint: MovieEndpoint

    @MockBean
    private lateinit var movieService: MovieService

    private lateinit var client: WebTestClient
    private val baseUrl = "http://localhost:8080/api/movies/"
    private val jackson = jacksonObjectMapper()

    @PostConstruct
    @Suppress("unused")
    private fun buildClient() {
        client = WebTestClient.bindToController(movieEndpoint).build()
    }

    @Test
    fun `get all movies test`() {
        // given
        val movie = movie()

        // when
        whenever(movieService.getAllMovies()) doReturn Flux.just(movie)

        // then
        client.get()
            .uri(baseUrl)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(MovieDto::class.java)
            .hasSize(1)
            .contains(movie.dto())
    }

    @Test
    fun `get movie by title test`() {
        // given
        val movie = movie()

        // when
        whenever(movieService.findByTitle(movie.title)) doReturn Flux.just(movie)

        //then
        client.get()
            .uri(baseUrl+"title/${movie.title}")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(MovieDto::class.java)
            .hasSize(1)
            .contains(movie.dto())
    }

    @Test
    fun `get movie details test`() {
        // given
        val movie = movie()

        // when
        whenever(movieService.getMovie(movie.id)) doReturn Mono.just(movie)

        //then
        client.get()
            .uri(baseUrl + movie.id)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(jackson.writeValueAsString(movie.dto()))
    }

    @Test
    fun `add movie test`() {
        // given
        val command = movieCommand()
        val movie = Movie.fromCommand(command)

        // when
        whenever(movieService.saveMovie(command)) doReturn Mono.just(movie)

        //then
        client.post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(command), MovieCommand::class.java)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .json(jackson.writeValueAsString(movie.dto()))
    }
}