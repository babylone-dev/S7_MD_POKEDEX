package co.babylone.pokedex

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val sharedPref = this.getSharedPreferences("co.babylone.pokedex", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")

        val displayUsernameTextView = findViewById<TextView>(R.id.display_username)
        displayUsernameTextView.text = username


        Snackbar.make(findViewById(R.id.display_username), "Bienvenue $username", Snackbar.LENGTH_SHORT).show()
    }
}