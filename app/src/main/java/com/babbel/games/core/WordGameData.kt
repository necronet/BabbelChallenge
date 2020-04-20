package com.babbel.games.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
/**
 * Data class that follow the Structure
 *
 * {text_eng:String, text_spa:String}
 *
 */

@JsonClass(generateAdapter = true)
data class WordGameData(@Json(name = "text_eng") val text_eng:String, @Json(name = "text_spa") val text_spa:String)
