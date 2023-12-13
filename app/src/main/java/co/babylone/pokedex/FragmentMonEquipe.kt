package co.babylone.pokedex


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Pokeapi
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMonEquipe : Fragment(R.layout.fragment_mon_equipe) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewMonEquipe)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        CoroutineScope(Dispatchers.IO).launch {
            val pokeapi = Pokeapi()
            val favoritePokemonList = pokeapi.getFavoritePokemon(requireContext())


            withContext(Dispatchers.Main) {
                pokemonAdapter = PokemonAdapter(favoritePokemonList)
                recyclerView.adapter = pokemonAdapter
            }
        }
    }
}
