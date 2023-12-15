package co.babylone.pokedex

import Pokeapi
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test

class PokeApiTest {
    @Test
    fun idPokemon_isCorrect() {
        CoroutineScope(Dispatchers.IO).launch {
            val context = InstrumentationRegistry.getInstrumentation().getTargetContext()
            val listid = listOf(1, 2, 3)
            val pokeapi = Pokeapi(context)
            val pokemons = pokeapi.getPokedex()

            Assert.assertEquals(listid.size, pokemons.size)

            for (i in listid.indices) {
                Assert.assertEquals(listid[i], pokemons[i].id)
            }
        }
    }


    @Test
    fun idPokemon_isUncorrect() {
        CoroutineScope(Dispatchers.IO).launch {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val listid = listOf(2000, 3000, 4000)
            val pokeapi = Pokeapi(context)
            val pokemons = pokeapi.getPokedex()

            Assert.assertEquals(listid.size, pokemons.size)

            for (i in listid.indices) {
                Assert.assertEquals(listid[i], pokemons[i].id)
            }
        }
    }
    @Test
    fun pokemonName_isCorrect() {
        CoroutineScope(Dispatchers.IO).launch {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val expectedNames = listOf("Bulbasaur", "Ivysaur", "Venusaur")
            val pokeapi = Pokeapi(context)
            val pokemons = pokeapi.getPokedex()

            Assert.assertEquals(expectedNames.size, pokemons.size)

            for (i in expectedNames.indices) {
                Assert.assertEquals(expectedNames[i], pokemons[i].name)
            }
        }
    }
    @Test
    fun pokemonName_isIncorrect() {
        CoroutineScope(Dispatchers.IO).launch {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val expectedNames = listOf("Sylvain", "Yann", "")
            val pokeapi = Pokeapi(context)
            val pokemons = pokeapi.getPokedex()

            Assert.assertEquals(expectedNames.size, pokemons.size)

            for (i in expectedNames.indices) {
                Assert.assertEquals(expectedNames[i], pokemons[i].name)
            }
        }
    }

}