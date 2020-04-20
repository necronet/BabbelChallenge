package com.babbel.games.core
/**
 * Represent a message to be display with the output for success or failure depending
 * on the result from play() method at PlayGame class
 * */
sealed class PlayMessage {

    companion object {
        fun createMessage(success: Boolean): PlayMessage = when(success) {
            true -> PlayMessageSuccess()
            false -> PlayMessageFailure()
        }
    }

    class PlayMessageSuccess : PlayMessage()
    class PlayMessageFailure : PlayMessage()
}
