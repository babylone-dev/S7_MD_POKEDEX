package co.babylone.pokedex

import Pokeapi
import Pokemon
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class PokemonAdapter(private val pokemonList: MutableList<Pokemon>, val isPokedex: Boolean) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_tile, parent, false)
        return PokemonViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.bind(currentItem, holder.itemView.context)
    }

    override fun getItemCount() = pokemonList.size

    inner class PokemonViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagePokemon)
        private val textViewName: TextView = itemView.findViewById(R.id.textPokemonName)
        private val textPokemonId: TextView = itemView.findViewById(R.id.textPokemonId)
        private val toggleShinyButton: ToggleButton = itemView.findViewById(R.id.toggleShiny)
        private val addToFavorite: ToggleButton = itemView.findViewById(R.id.btnFavorite)

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

            addToFavorite.setOnClickListener {
                val currentItem = pokemonList[adapterPosition]
                val pokeapi = Pokeapi()
                val favoritePokemonIds = pokeapi.getFavoritePokemonIds(context)
                if (!favoritePokemonIds.contains(currentItem.id)) {
                    currentItem.addToFavorite(context)
                } else {
                    currentItem.removeFromFavorite(context)
                    if (!isPokedex) {
                        val index = favoritePokemonIds.indexOf(currentItem.id)
                        pokemonList.removeAt(index)
                        notifyItemRemoved(index)
                    }
                }
                currentItem.toggleFavorite()
            }
        }

        fun bind(pokemon: Pokemon, context: Context) {
            textViewName.text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            textPokemonId.text = "#${pokemon.id.toString().padStart(3, '0')}"
            if (pokemon.isShiny) {
                Picasso.get().load(pokemon.shiny).into(imageView)
            } else {
                Picasso.get().load(pokemon.image).into(imageView)
            }
            val pokeapi = Pokeapi()
            val favoritePokemonIds = pokeapi.getFavoritePokemonIds(context)
            addToFavorite.isChecked = favoritePokemonIds.contains(pokemon.id)
            toggleShinyButton.isChecked = pokemon.isShiny
        }
    }
}