package co.babylone.pokedex


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Pokeapi
import android.content.Context
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMonEquipe : Fragment(R.layout.fragment_mon_equipe) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = view.context.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        view.findViewById<TextView>(R.id.textViewEquipeTitle).text = username

        recyclerView = view.findViewById(R.id.recyclerViewMonEquipe)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        CoroutineScope(Dispatchers.IO).launch {
            val pokeapi = Pokeapi(view.context)
            pokeapi.getPokedex()
            val pokemonList = pokeapi.getFavoritePokemon()

            withContext(Dispatchers.Main) {
                pokemonAdapter = PokemonAdapter(pokemonList, false)
                recyclerView.adapter = pokemonAdapter
            }
        }
    }
}
