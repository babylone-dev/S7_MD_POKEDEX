package co.babylone.pokedex

import Pokemon
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        holder.textViewName.text = currentItem.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        holder.textPokemonId.text = "#${currentItem.id.toString().padStart(3, '0')}"
        Picasso.get().load(currentItem.image).into(holder.imagePokemon)
    }

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagePokemon)
        val textViewName: TextView = itemView.findViewById(R.id.textPokemonName)
        val textPokemonId: TextView = itemView.findViewById(R.id.textPokemonId)
        val imagePokemon: ImageView = itemView.findViewById(R.id.imagePokemon)
    }
}
