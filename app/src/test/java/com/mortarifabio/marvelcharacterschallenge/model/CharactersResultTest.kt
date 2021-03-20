package com.mortarifabio.marvelcharacterschallenge.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CharactersResultTest {

    @Test
    fun areItemsTheSame_sameIdsShouldReturnTrue() {
        val spiderPig = CharactersResult("The pig is in the web", 27L, "Spider-Pig", Thumbnail("spiderpig-thumb", "jpg"))
        val ironPig = CharactersResult("Pig made of steel", 27L, "Iron-Pig", Thumbnail("ironpig-thumb", "jpg"))

        val compare = CharactersResult.CharactersResultComparator.areItemsTheSame(spiderPig, ironPig)
        assertEquals(true, compare)
    }

    @Test
    fun areItemsTheSame_diferentIdsShouldReturnFalse() {
        val spiderPig1 = CharactersResult("The pig is in the web", 27L, "Spider-Pig", Thumbnail("spiderpig-thumb", "jpg"))
        val spiderPig2 = CharactersResult("The pig is in the web", 12L, "Spider-Pig", Thumbnail("spiderpig-thumb", "jpg"))

        val compare = CharactersResult.CharactersResultComparator.areItemsTheSame(spiderPig1, spiderPig2)
        assertEquals(false, compare)
    }

    @Test
    fun areContentsTheSame_diferentContentShouldReturnFalse() {
        val spiderPig = CharactersResult("The pig is in the web", 27L, "Spider-Pig", Thumbnail("spiderpig-thumb", "jpg"))
        val ironPig = CharactersResult("Pig made of steel", 27L, "Iron-Pig", Thumbnail("ironpig-thumb", "jpg"))

        val compare = CharactersResult.CharactersResultComparator.areContentsTheSame(spiderPig, ironPig)
        assertEquals(false, compare)
    }

    @Test
    fun areContentsTheSame_sameContentsShouldReturnTrue() {
        val spiderPig1 = CharactersResult("The pig is in the web", 27L, "Spider-Pig", Thumbnail("spiderpig-thumb", "jpg"))
        val spiderPig2 = CharactersResult("The pig is in the web", 27L, "Spider-Pig", Thumbnail("spiderpig-thumb", "jpg"))

        val compare = CharactersResult.CharactersResultComparator.areItemsTheSame(spiderPig1, spiderPig2)
        assertEquals(true, compare)
    }

}