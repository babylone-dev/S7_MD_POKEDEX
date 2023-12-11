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
    lateinit var binding: HomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getSharedPreferences("co.babylone.pokedex", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")

        val displayUsernameTextView = binding.displayUsername
        displayUsernameTextView.text = username
        Snackbar.make(displayUsernameTextView, "Bienvenue $username", Snackbar.LENGTH_SHORT).show()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuMonPokedex -> {
                    replaceFragment(FragmentPokedex())
                    true
                }
                R.id.menuMonEquipe -> {
                    replaceFragment(FragmentMonEquipe())
                    true
                }
                else -> false
            }
        }

        // Open FragmentPokedex when HomeActivity is first created
        if (savedInstanceState == null) {
            replaceFragment(FragmentPokedex())
            binding.bottomNavigationView.selectedItemId = R.id.menuMonPokedex
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, fragment)
        fragmentTransaction.commit()
    }
}