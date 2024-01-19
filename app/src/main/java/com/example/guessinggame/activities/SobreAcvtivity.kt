package com.example.guessinggame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guessinggame.R

class SobreAcvtivity : AppCompatActivity() {
    var temSom = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre_acvtivity)

    }

    fun Menu() {
        temSom = intent.getBooleanExtra("temSom",false)
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("temSom", temSom)
        startActivity(intent)
    }
    override fun onBackPressed() {
        // não chame o super desse método
        Menu()
    }
    fun Menu(view: View) {
        // Aqui você pode chamar a função Menu que espera um parâmetro view
        Menu()
    }
}