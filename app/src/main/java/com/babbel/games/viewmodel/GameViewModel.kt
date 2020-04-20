package com.babbel.games.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.babbel.games.R
import com.babbel.games.core.PlayMessage
import com.babbel.games.core.WordGameEngine
import com.babbel.games.core.WordPlay
import okio.Okio

class GameViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var engine: WordGameEngine
    lateinit var currentPlay : WordPlay
    var wins : Int = 0
    var lost : Int = 0

    val wordPlay: LiveData<WordPlay> = liveData {
        val wordPlay = loadGame()
        emit(wordPlay)
    }

    private val result: MutableLiveData<Pair<Int, Int>> by lazy {
        Log.d("DEBUG", "Result of muttable")
        MutableLiveData<Pair<Int, Int>>().also {
            it.value = Pair(wins, lost)
        }
    }

    fun getResult() : LiveData<Pair<Int, Int>> {
        return result
    }

    fun loadGame():WordPlay {
        val stream = Okio.buffer(Okio.source(app.resources.openRawResource(R.raw.words_v2)))
        engine = WordGameEngine(stream)
        currentPlay = engine.start().next()
        return currentPlay
    }

    fun answer(isCorrect: Boolean) : PlayMessage {
        val playMessage = currentPlay.play(isCorrect)
        if (playMessage is PlayMessage.PlayMessageSuccess) {
            Log.d("DEBUG","Success")
            wins += 1
            result.postValue(Pair(wins, lost))

        }
        if (playMessage is PlayMessage.PlayMessageFailure) {
            Log.d("DEBUG","Failure")
            lost += 1
            result.postValue(Pair(wins, lost))
        }
        return playMessage
    }
}