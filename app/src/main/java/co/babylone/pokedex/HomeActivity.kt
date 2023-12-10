package co.babylone.pokedex

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.babylone.pokedex.databinding.FragmentPokedexBinding
import co.babylone.pokedex.databinding.HomeScreenBinding
import com.google.android.material.snackbar.Snackbar


class HomeActivity : AppCompatActivity() {
    //tuto que j'ai check https://www.youtube.com/watch?v=h-NcxT697Nk
    lateinit var binding: HomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val sharedPref = this.getSharedPreferences("co.babylone.pokedex", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")

        val displayUsernameTextView = findViewById<TextView>(R.id.display_username)
        displayUsernameTextView.text = username


        Snackbar.make(findViewById(R.id.display_username), "Bienvenue $username", Snackbar.LENGTH_SHORT).show()

        binding = HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnMonPokedex = binding.bottomNavigationView.findViewById<Button>(R.id.btnMonPokedex)

        // Set the onClickListener for the button
        btnMonPokedex.setOnClickListener {
        replaceFragment(FragmentPokedex())
        }

        val btnMonEquipe = binding.bottomNavigationView.findViewById<Button>(R.id.btnMonEquipe)

        btnMonEquipe.setOnClickListener {
            replaceFragment(FragmentMonEquipe())

        }


    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, fragment)
        fragmentTransaction.commit()
    }
}