package co.babylone.pokedex

class Pokeapi {
    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    fun getPokedex() {
        val url = "${BASE_URL}pokemon?limit=151"

    }
}

class Pokemon(val name: String, val id: Int, val image: String) {

}