package co.babylone.pokedex

import Pokemon
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Test
import co.babylone.pokedex.MainActivity.Companion.isValid
import org.junit.Before

class PokeApiUnitTest {
    @Test
    fun testValidUsernameFormat() {
        val validUsername = "Coco"
        assertTrue(isValid(validUsername))
    }
    @Test
    fun testInvalidUsernameFormat(){
        val invalidUsername = ""
        assertFalse(isValid(invalidUsername))
    }
    private lateinit var pikachu: Pokemon

    @Before
    fun setUp() {
        pikachu = Pokemon("pikachu", 25, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/25.png")
    }

    @Test
    fun toggleFavorite_shouldToggleIsFavorite() {
        assertFalse(pikachu.isFavorite)

        pikachu.toggleFavorite()
        assertTrue(pikachu.isFavorite)

        pikachu.toggleFavorite()
        assertFalse(pikachu.isFavorite)
    }
}