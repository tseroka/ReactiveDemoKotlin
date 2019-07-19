package com.example.reactive.kotlin.movie

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface MovieRepository : ReactiveMongoRepository<Movie, String> {

    fun findByTitle(title: String): Flux<Movie>
}