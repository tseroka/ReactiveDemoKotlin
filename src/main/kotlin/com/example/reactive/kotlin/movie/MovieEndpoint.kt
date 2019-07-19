package com.example.reactive.kotlin.movie

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Suppress("unused")
@RestController
@RequestMapping("/api/movies")
class MovieEndpoint(private val movieService: MovieService) {

    @GetMapping
    fun getMovies() = movieService.getAllMovies().toDto()

    @GetMapping("title/{title}")
    fun findByTitle(@PathVariable title: String) = movieService.findByTitle(title).toDto()

    @GetMapping("{id}")
    fun getMovieDetails(@PathVariable id: String) = movieService.getMovie(id).toDto()

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun addMovie(@RequestBody movie: MovieCommand) = movieService.saveMovie(movie).toDto()

    @PostMapping("{id}/rate")
    fun addRate(
        @PathVariable id: String,
        @RequestBody rate: Int
    ) = movieService.addRate(id, rate).toDto()

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    fun deleteMovie(@PathVariable id: String) = movieService.deleteMovie(id)

    @GetMapping("genres")
    fun getGenres() = Flux.fromIterable(Genre.values().toList())

    private fun Flux<Movie>.toDto() = this.map(Movie::dto)

    private fun Mono<Movie>.toDto() = this.map(Movie::dto)
}