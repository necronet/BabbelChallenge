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
    var wins : Int = 0
    var lost : Int = 0

    private val wordPlay: MutableLiveData<WordPlay> by lazy {
        val wordPlay = loadGame()
        MutableLiveData<WordPlay>().apply {
            value = wordPlay
        }
    }

    fun getWordPlay(): LiveData<WordPlay> {
        return wordPlay
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

    fun loadGame(): WordPlay {
        val stream = Okio.buffer(Okio.source(app.resources.openRawResource(R.raw.words_v2)))
        engine = WordGameEngine(stream)
        return engine.start().next()
    }

    fun answer(isCorrect: Boolean) : PlayMessage? {

        val playMessage = wordPlay.value?.play(isCorrect)
        if (playMessage is PlayMessage.PlayMessageSuccess) {
            win()
        }
        if (playMessage is PlayMessage.PlayMessageFailure) {
            lose()
        }

        wordPlay.postValue(engine.next())
        return playMessage
    }

    fun win() {
        wins += 1
        updateResult()
    }

    fun lose() {
        lost += 1
        updateResult()
    }

    fun updateResult() {
        result.postValue(Pair(wins, lost))
    }

}