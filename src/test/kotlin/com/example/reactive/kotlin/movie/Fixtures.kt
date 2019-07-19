package com.example.reactive.kotlin.movie

import java.time.LocalDate

fun movie(
    title: String = "Anything",
    director: String = "Anyone",
    releaseDate: LocalDate = LocalDate.now()
) = Movie(
    title = title,
    director = director,
    releaseDate = releaseDate,
    genre = Genre.Action
)

fun movieCommand(
    title: String = "Anything",
    director: String = "Anyone",
    releaseDate: String = "2018-01-01",
    genre: String = "Action"
) = MovieCommand(
    title,
    director,
    releaseDate,
    genre
)