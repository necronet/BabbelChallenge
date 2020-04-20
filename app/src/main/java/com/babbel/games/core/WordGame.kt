package com.babbel.games.core

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.BufferedSource

class WordGame private constructor(val data: List<WordGameData>?) {

    fun selectRandomWord(): Pair<WordGameData?, String?> {
        return Pair(data?.random(), data?.random()?.text_spa)
    }

    data class Builder(var source: BufferedSource) {
        fun build(): WordGame {
            val moshi: Moshi = Moshi.Builder().build()
            val listType = Types.newParameterizedType(List::class.java, WordGameData::class.java)
            val adapter: JsonAdapter<List<WordGameData>> = moshi.adapter(listType)
            return WordGame(adapter.fromJson(source))
        }
    }

}