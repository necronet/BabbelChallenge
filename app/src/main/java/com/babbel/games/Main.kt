package com.babbel.games

import com.babbel.games.core.WordGameEngine
import okio.Okio
import java.io.File

fun main() {

    val wordFile = "/Users/joseayerdis/Desktop/words_v2.json"

    WordGameEngine(Okio.buffer(Okio.source(File(wordFile)))).apply {
        start()
        print(next().play(true))
    }

}