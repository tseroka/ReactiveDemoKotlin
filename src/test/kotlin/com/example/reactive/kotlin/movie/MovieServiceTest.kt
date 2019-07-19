package com.example.reactive.kotlin.movie

import com.nhaarman.mockito_kotlin.mock

class MovieServiceTest {

    private val movieRepository = mock<MovieRepository>()
    private val movieService = MovieService(movieRepository)
}