package com.ittybittyapps.collection.widget.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

class DataRepository(scope: CoroutineScope) {

    data class Data(val name: String)

    val data = flow {
        while (true) {
            // Emit 5-10 random names every 5-10 seconds
            delay(Random.nextLong(5000L..10000L))
            emit(names
                .shuffled()
                .take(Random.nextInt(5..10))
                .map { Data(it) }
            )
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
}

private val names = listOf(
    "Sylvia",
    "Brody",
    "Cindy",
    "Jamari",
    "Maritza",
    "Vivienne",
    "Nixon",
    "Vivian",
    "Arav",
    "Cristiano",
    "Marques",
    "Nyla",
    "Darien",
    "Randall",
    "Darwin",
    "Rochel",
    "Denise",
    "Millie",
    "Journey",
    "Elora",
    "Justice",
    "Jayden",
    "Kailee",
    "Elliott",
    "Ivy",
    "Jakob",
    "Solomon",
    "Kalen",
    "Anita",
    "Peyton",
    "Hallie",
    "Kacie",
    "Beverly",
    "Janice",
    "Avianna",
    "Gian",
    "Ada",
    "Wilmer",
    "Noam",
    "Joel",
    "Harley",
    "Samantha",
    "Adolfo",
    "Lainey",
    "Carina",
    "Zavion",
    "Darrius",
    "Greysen",
    "Noel",
    "Juliet"
)
