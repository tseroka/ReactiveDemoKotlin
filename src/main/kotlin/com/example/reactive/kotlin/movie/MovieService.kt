package com.example.reactive.kotlin.movie

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MovieService(
    private val movieRepository: MovieRepository
) {
    fun saveMovie(command: MovieCommand): Mono<Movie> = movieRepository.save(
        Movie.fromCommand(command)
    ).also {
        it.subscribe()
    }

    fun getAllMovies(): Flux<Movie> = movieRepository.findAll()

    fun findByTitle(title: String): Flux<Movie> = movieRepository.findByTitle(title)

    fun getMovie(id: String): Mono<Movie> = movieRepository.findById(id)

    fun addRate(id: String, rate: Int): Mono<Movie> {
        return editMovie(id) {
            this.rate.addRate(rate)
        }
    }

    private fun editMovie(id: String, editAction: Movie.() -> Unit): Mono<Movie> {
        return movieRepository.findById(id).doOnSuccess {
            it.editAction()
            movieRepository.save(it).subscribe()
        }
    }

    fun deleteMovie(id: String) {
        movieRepository.deleteById(id)
    }
}
