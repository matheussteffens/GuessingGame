package com.example.guessinggame.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guessinggame.R

class Nome : AppCompatActivity() {
    lateinit var txNome : EditText
    lateinit var teste : TextView
    var temSom = true
    var nome = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nome)
        txNome = findViewById<EditText>(R.id.editTextTextMultiLine)
        teste = findViewById<TextView>(R.id.nome)


        temSom = intent.getBooleanExtra("temSom",true)
        intent  = Intent(this, Jogo::class.java)
        intent.putExtra("temSom",temSom)


    }
    override fun onBackPressed() {
        // não chame o super desse método
        Menu()
    }
    fun EscreverNome(){
        val view: View? = this.currentFocus
        val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        if(txNome.length()<3) {
           Toast.makeText(this, "Nome deve conter 3 caracteres ou mais ", Toast.LENGTH_LONG).show()

        } else if(txNome.length()>10){
            Toast.makeText(this, "Nome não pode ter mais de 10 caracteres", Toast.LENGTH_LONG).show()
        }else {
            nome = txNome.text.toString()
            intent.putExtra("nome",nome)
            startActivity(intent)
        }
    }
    fun Salvar(view: View) {
        EscreverNome()
        // startActivity(intent)
    }

    fun Menu() {
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("temSom", temSom)
        startActivity(intent)}
    fun Menu(view: View) {
        // Aqui você pode chamar a função Menu que espera um parâmetro view
        Menu()
    }
}