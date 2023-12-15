package co.babylone.pokedex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        val sharedPref = this.getSharedPreferences("co.babylone.pokedex", Context.MODE_PRIVATE)
        val loginButton = findViewById<Button>(R.id.login_button)
        val username = findViewById<EditText>(R.id.username)
        username.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                loginButton.performClick()
                return@setOnEditorActionListener true
            }
            false
        }
        loginButton.setOnClickListener {
            val usernameValue = username.text.toString()
            if (isValid(usernameValue)) {
                with (sharedPref.edit()) {
                    putString("username", usernameValue)
                    apply()
                }
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Snackbar.make(findViewById(R.id.username), "Veuillez entrer un pseudonyme !", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        fun isValid(username: String): Boolean {
            return username.isNotEmpty()
        }
    }
}