package co.babylone.pokedex

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            if (username.isNotEmpty()) {
                Snackbar.make(findViewById(R.id.username), "Bienvenue $username", Snackbar.LENGTH_SHORT).show()
                with (sharedPref.edit()) {
                    putString("username", username)
                    apply()
                }
                switchToMainActivity()
            } else {
                Snackbar.make(findViewById(R.id.username), "Veuillez entrer un pseudonyme", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun switchToMainActivity() {
        setContentView(R.layout.activity_main)
    }
}