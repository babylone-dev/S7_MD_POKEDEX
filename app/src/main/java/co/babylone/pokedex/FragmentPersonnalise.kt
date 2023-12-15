package co.babylone.pokedex

import Pokeapi
import Pokemon
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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
        val url = view?.findViewById<EditText>(R.id.editTextURL)?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val customPokemon = Pokemon(name, null, url, null)
            val pokeApi = Pokeapi(context)
            pokeApi.getPokedex()
            pokeApi.addCustomPokemon(customPokemon)
        }
    }
}