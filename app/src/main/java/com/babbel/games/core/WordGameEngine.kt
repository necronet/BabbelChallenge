package com.babbel.games.core

import okio.BufferedSource
import kotlin.random.Random

/**
 * Represents an engine that is capable of producing several plays on the fly.
 * Each WordPlay contains the information to decide wether or not a the translated word will be display
 * or a random word from the file will be display.
 *
 * Each time the next() method is call a new WordPlay will be generated, and randomly choose wether
 * the translation will be correct or not based on Random.nextBoolean method
 *
 * */
class WordGameEngine(val stream: BufferedSource) {
    lateinit var wordGame: WordGame

    fun start() {
        wordGame = WordGame.Builder(stream).build()
    }

    fun next(): WordPlay {
        val (wordGameData, randomWord) = wordGame.selectRandomWord()
        wordGameData?.let {
            return WordPlay(
                it,
                randomWord,
                Random.nextBoolean()
            )
        }
        throw RuntimeException("An error had occured on generating the next word game play")
    }

}

//Represent each play of the game with the challenge answer/right answer/random pick word
class WordPlay(val wordGame: WordGameData, val randomWord: String?, val chooseTranslation: Boolean) {
    fun play(answer: Boolean) = PlayMessage.createMessage(answer == chooseTranslation)
}


