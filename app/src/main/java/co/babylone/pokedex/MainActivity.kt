package co.babylone.pokedex

import Pokeapi
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        val sharedPref = this.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            if (username.isNotEmpty()) {
                with (sharedPref.edit()) {
                    putString("username", username)
                    apply()
                }
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Snackbar.make(findViewById(R.id.username), "Veuillez entrer un pseudonyme", Snackbar.LENGTH_SHORT).show()
            }
        }



    }
}