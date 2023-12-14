import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Pokeapi () {
    var pokemons = ArrayList<Pokemon>()
    var client: OkHttpClient = OkHttpClient()

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    suspend fun getPokedex(): MutableList<Pokemon> = withContext(Dispatchers.IO) {
        val url = "${BASE_URL}pokemon?limit=151"
        val request = Request.Builder().url(url).build()

        val response = suspendCoroutine<Response> { continuation ->
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }
            })
        }

        val body = response.body()?.string()
        val json = body?.let { JSONObject(it) }
        val results = json?.getJSONArray("results")
        if (results != null) {
            for (i in 0 until results.length()) {
                val pokemon = results.getJSONObject(i)
                val name = pokemon.getString("name")
                val url = pokemon.getString("url")
                val id = url.split("/")[6].toInt()
                val image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                val shiny = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png"
                pokemons.add(Pokemon(name, id, image, shiny))
            }
        }

        pokemons // Return the list of Pokemon
    }

    fun saveInCache(context: Context) {
        val sharedPref = context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("pokemons", pokemons.toString())
        editor.apply()
    }

    fun getFromCache(context: Context) {
        val sharedPref = context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val pokemonsString = sharedPref.getString("pokemons", "")

        if (!pokemonsString.isNullOrEmpty()) {
            val pattern = Regex("Pokemon\\(name=(\\w+), id=(\\d+), image=(.*?), shiny=(.*?)\\)")
            val matches = pattern.findAll(pokemonsString)

            val parsedPokemons = matches.map { matchResult ->
                val (name, id, image, shiny) = matchResult.destructured
                Pokemon(name, id.toInt(), image, shiny)
            }.toList()
            pokemons = ArrayList(parsedPokemons)
        }
    }

    fun getFavoritePokemon(context: Context): MutableList<Pokemon> {
        val sharedPref = context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val favoritePokemon = sharedPref.getStringSet("favoritePokemon", setOf())
        val favoritePokemonList = ArrayList<Pokemon>()
        for (pokemon in pokemons) {
            if (favoritePokemon?.contains(pokemon.id.toString()) == true) {
                favoritePokemonList.add(pokemon)
            }
        }
        return favoritePokemonList
    }

    fun getFavoritePokemonIds(context: Context): List<Int> {
        val sharedPref = context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val favoritePokemon = sharedPref.getStringSet("favoritePokemon", setOf())
        val favoritePokemonList = ArrayList<Int>()
        for (element in favoritePokemon!!) {
            favoritePokemonList.add(element.toInt())
        }

        return favoritePokemonList
    }

    fun addCustomPokemon(pokemon: Pokemon) {
        pokemon.id = pokemons.size + 1
        pokemons.add(pokemon)
    }

}

class Pokemon(val name: String, var id: Int?, val image: String?, val shiny: String?, var isShiny: Boolean = false, var isFavorite: Boolean = false) {
    fun addToFavorite(context: Context) {
        val sharedPref = context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val favoritePokemon = sharedPref.getStringSet("favoritePokemon", setOf())
        val newFavoritePokemon = favoritePokemon?.toMutableSet()
        newFavoritePokemon?.add(id.toString())
        with (sharedPref.edit()) {
            putStringSet("favoritePokemon", newFavoritePokemon)
            apply()
            commit()
        }
    }

    fun removeFromFavorite(context: Context) {
        val sharedPref = context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val favoritePokemon = sharedPref.getStringSet("favoritePokemon", setOf())
        val newFavoritePokemon = favoritePokemon?.toMutableSet()
        newFavoritePokemon?.remove(id.toString())
        with (sharedPref.edit()) {
            putStringSet("favoritePokemon", newFavoritePokemon)
            apply()
            commit()
        }
    }

    override fun toString(): String {
        return "Pokemon(name=$name, id=$id, image=$image, shiny=$shiny)"
    }

    fun toggleShiny(){
        isShiny = !isShiny
    }

    fun toggleFavorite(){
        isFavorite = !isFavorite
    }

}





