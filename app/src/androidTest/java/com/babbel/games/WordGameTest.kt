package com.babbel.games

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.babbel.games.core.WordGame
import okio.Okio
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WordGameTest {

    @Test
    fun testWordGameDataToJson() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val fileStream = appContext.resources.openRawResource(R.raw.words_v2)
        val wordGame = WordGame.Builder(Okio.buffer(Okio.source(fileStream))).build()
        val (_, randomWord) = wordGame.selectRandomWord()

        assertNotNull(wordGame)
        assertNotNull(randomWord)
    }

}
