package com.example.guessinggame.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.guessinggame.R
import com.example.guessinggame.database.DatabaseHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Terminou : AppCompatActivity() {
   lateinit var dbHelper: DatabaseHelper
    lateinit var pontuacao : TextView
    lateinit var txtNome : TextView
    var temSom = true
    var nome = ""
    var pontos = 0
    val dateTime = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a"))


    private var mediaPlayer: MediaPlayer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terminou)
        pontuacao = findViewById(R.id.pontosTerminou)
        txtNome = findViewById(R.id.parabensTerminou)

        temSom = intent.getBooleanExtra("temSom",true)
        nome = intent.getStringExtra("nome").toString()
        pontos = intent.getIntExtra("pontos",0)

        //Para trocar o som, basta colar o som na pasta "raw"
        //aqui no códio substiruir "R.raw.sons" por "R.raw. + nome do som escolhido"
        mediaPlayer = MediaPlayer.create(this, R.raw.parabens)


        pontuacao.text = pontos.toString()
        txtNome.text = nome

        dbHelper = DatabaseHelper(this)

        if(temSom)
            playSound()

       AddDados()

    }
    override fun onBackPressed() {
        // não chame o super desse método
        Menu()
    }
    private fun playSound() {
        mediaPlayer?.start()
    }
    private fun stopSound(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }
    fun Menu() {
        stopSound()
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("temSom", temSom)
        startActivity(intent)
    }
    fun Menu(view: View) {
        // Aqui você pode chamar a função Menu que espera um parâmetro view
        Menu()
    }
    fun AddDados(){
// Add a new document with a generated ID
        dbHelper.addRecord(nome,  pontos , dateTime)
    }
}