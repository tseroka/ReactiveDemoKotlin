package com.example.reactive.kotlin.movie

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
data class Movie(
    @Id val id: String = UUID.randomUUID().toString(),
    val title: String,
    val director: String,
    val releaseDate: LocalDate,
    val genre: Genre
) {
    companion object {
        fun fromCommand(command: MovieCommand) = with(command) {
            Movie(
                title = title,
                director = director,
                releaseDate = LocalDate.parse(releaseDate),
                genre = Genre.valueOf(genre)
            )
        }
    }

    val rate: Rate = Rate()

    fun dto() = MovieDto(
        id,
        title,
        director,
        releaseDate.toString(),
        genre,
        rate.totalRating,
        rate.ratingCount
    )
}

class Rate {
    private var ratingSum: Long = 0

    var ratingCount: Long = 0
        private set

    val totalRating: Double
        get() = if (ratingCount != 0L) ratingSum.toDouble() / this.ratingCount else 0.0

    fun addRate(rate: Int) {
        ratingSum += rate.toLong()
        this.ratingCount += 1
    }
}

enum class Genre {
    Action, Horror, Thriller;
}

data class MovieDto(
    val id: String,
    val title: String,
    val director: String,
    val releaseDate: String,
    val genre: Genre,
    val rating: Double,
    val ratingCount: Long
)

data class MovieCommand(
    val title: String,
    val director: String,
    val releaseDate: String,
    val genre: String
)
