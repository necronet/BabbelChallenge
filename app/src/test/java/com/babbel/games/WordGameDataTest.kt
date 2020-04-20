package com.babbel.games

import com.babbel.games.core.WordGameData
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test


/**
 * WordGameData test for json parsing  using moshi
 */

class WordGameDataTest {

    val EXPECTED_JSON = "{\"text_eng\":\"hello\",\"text_spa\":\"hola\"}"

    @Test
    fun testWordGameDataToJson() {

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(WordGameData::class.java)
        val wordGameData = WordGameData("hello","hola")
        assertEquals(EXPECTED_JSON, adapter.toJson(wordGameData))
    }

    @Test
    fun testWordGameDataFromJson() {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(WordGameData::class.java)

        val wordGameData = adapter.fromJson(EXPECTED_JSON)
        assertNotNull(wordGameData)
    }
}
