package co.babylone.pokedex

import Pokeapi
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentPokedex : Fragment(R.layout.fragment_pokedex) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewPokedex)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        CoroutineScope(Dispatchers.IO).launch {
            val pokeapi = Pokeapi()
            val pokemonList = pokeapi.getPokedex()
            withContext(Dispatchers.Main) {
                pokemonAdapter = PokemonAdapter(pokemonList, true)
                recyclerView.adapter = pokemonAdapter
            }
        }
    }
}
