package co.babylone.pokedex

import Pokemon
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import com.squareup.picasso.Picasso

class PokemonAdapter(private val pokemonList: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_tile, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = pokemonList.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagePokemon)
        private val textViewName: TextView = itemView.findViewById(R.id.textPokemonName)
        private val textPokemonId: TextView = itemView.findViewById(R.id.textPokemonId)
        private val toggleShinyButton: ToggleButton = itemView.findViewById(R.id.toggleShiny)

        init {
            toggleShinyButton.setOnClickListener {
                // Toggle shiny status when the button is clicked
                val currentItem = pokemonList[adapterPosition]
                currentItem.toggleShiny()

                // Update the image based on the new shiny status
                if (currentItem.isShiny) {
                    Picasso.get().load(currentItem.shiny).into(imageView)
                } else {
                    Picasso.get().load(currentItem.image).into(imageView)
                }
            }
        }

        fun bind(pokemon: Pokemon) {
            textViewName.text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            textPokemonId.text = "#${pokemon.id.toString().padStart(3, '0')}"
            if (pokemon.isShiny) {
                Picasso.get().load(pokemon.shiny).into(imageView)
            } else {
                Picasso.get().load(pokemon.image).into(imageView)
            }
            toggleShinyButton.isChecked = pokemon.isShiny
        }
    }
}