package co.babylone.pokedex

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
class FragmentPersonnalise : Fragment(R.layout.fragment_personnalise){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonSendData: Button = view.findViewById(R.id.buttonSendData)
        buttonSendData.setOnClickListener {
            sendDataToFragmentMonEquipe()
        }
    }

    private fun sendDataToFragmentMonEquipe() {
        val name = view?.findViewById<EditText>(R.id.editTextNom)?.text.toString()
        val url = view?.findViewById<EditText>(R.id.editTextURL)?.text.toString()


        val fragmentMonEquipe = FragmentMonEquipe()


        val bundle = Bundle()
        bundle.putString("NAME_KEY", name)
        bundle.putString("URL_KEY", url)
        fragmentMonEquipe.arguments = bundle


        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.flFragment, fragmentMonEquipe)
            ?.addToBackStack(null)
            ?.commit()
    }
}