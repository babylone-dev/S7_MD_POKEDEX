package co.babylone.pokedex

import Pokeapi
import Pokemon
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentPersonnalise : Fragment(R.layout.fragment_personnalise){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonSendData: Button = view.findViewById(R.id.buttonSendData)
        buttonSendData.setOnClickListener {
            sendDataToFragmentMonEquipe(requireContext())
        }
    }

    private fun sendDataToFragmentMonEquipe(context : Context) {
        val name = view?.findViewById<EditText>(R.id.editTextNom)?.text.toString()
        var url = view?.findViewById<EditText>(R.id.editTextURL)?.text.toString()
        val defaultPokemon = Pokemon("Ditto", 132, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png")

        if (name.isEmpty() ){
            Snackbar.make(requireView(), "Veuillez entrer un nom", Snackbar.LENGTH_SHORT).show()
            return
        }

        if (url.isEmpty() ){
            url = defaultPokemon.image!!
        }

        CoroutineScope(Dispatchers.IO).launch {
            val customPokemon = Pokemon(name, null, url, defaultPokemon.shiny)
            val pokeApi = Pokeapi(context)
            pokeApi.getPokedex()
            pokeApi.addCustomPokemon(customPokemon)
        }
    }
}