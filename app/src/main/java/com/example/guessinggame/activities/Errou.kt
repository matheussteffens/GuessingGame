package com.example.guessinggame.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.guessinggame.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.guessinggame.database.DatabaseHelper


class Errou : AppCompatActivity() {
    lateinit var dbHelper: DatabaseHelper
    lateinit var pontuacao : TextView
    lateinit var txtNome : TextView
    var temSom = true
    var nome = ""
    var pontos = 0
    val dateTime = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_errou)
        pontuacao = findViewById(R.id.pontosErrou)
        txtNome = findViewById(R.id.parabensErrou)

        temSom = intent.getBooleanExtra("temSom",true)
        nome = intent.getStringExtra("nome").toString()
        pontos = intent.getIntExtra("pontos",0)


        pontuacao.text = pontos.toString()
        txtNome.text = nome

        if(temSom)
            playSound()
        if(pontos>0)
            AddDados()
    }
    override fun onBackPressed() {
        // não chame o super desse método
        Menu()
    }
    private fun playSound() {
        //Para trocar o som, basta colar o som na pasta "raw"
        //aqui no código substirui "R.raw.faustaoerrou" por "R.raw. + nome do som escolhido"
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.faustaoerrou)
        mediaPlayer.start()


    }
    fun Menu() {
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("temSom", temSom)
        startActivity(intent)
    }
    fun Menu(view: View) {
        // Aqui você pode chamar a função Menu que espera um parâmetro view
        Menu()
    }
    fun AddDados(){

        dbHelper = DatabaseHelper(this)
        dbHelper.addRecord(nome,  pontos , dateTime)
    }
}