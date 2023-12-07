package co.babylone.pokedex

import Pokeapi
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        CoroutineScope(Dispatchers.Main).launch {
            main(context = this@MainActivity)
        }
        val sharedPref = this.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            if (username.isNotEmpty()) {
                with (sharedPref.edit()) {
                    putString("username", username)
                    apply()
                }
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Snackbar.make(findViewById(R.id.username), "Veuillez entrer un pseudonyme", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
suspend fun main(context: Context) {
    val pokeapi = Pokeapi()
    val pokemonList = pokeapi.getPokedex()

    for (pokemon in pokemonList) {
        println("Name: ${pokemon.name}, ID: ${pokemon.id}, Image: ${pokemon.image} Shiny: ${pokemon.shiny}")
    }

    pokemonList[0].addToFavorite(context)
    pokemonList[3].addToFavorite(context)
    pokemonList[6].addToFavorite(context)

    val favoritePokemon = pokeapi.getFavoritePokemon(context)
    println("Favorite Pokemon:")
    for (pokemon in favoritePokemon) {
        println("Name: ${pokemon.name}, ID: ${pokemon.id}, Image: ${pokemon.image} Shiny: ${pokemon.shiny}")
    }
}