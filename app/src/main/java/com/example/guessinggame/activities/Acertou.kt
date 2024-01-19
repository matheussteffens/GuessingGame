package com.example.guessinggame.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guessinggame.R

class Acertou : AppCompatActivity() {
    var nome = ""
    var pontos = 0
    var temSom = true
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acertou)



        /*val mp: MediaPlayer = MediaPlayer.create(this@Acertou, R.raw.somcerto)
        mp.setOnCompletionListener { mp -> mp.release() }
        mp.start()*/
        temSom = intent.getBooleanExtra("temSom",true)
        nome = intent.getStringExtra("nome").toString()
        pontos = intent.getIntExtra("pontos",0)
        if(temSom) {

           playSound()
        }
    }

    override fun onBackPressed() {
        // não chame o super desse método
        Voltar()
    }
    private fun playSound() {
        //Para trocar o som, basta colar o som na pasta "raw"
        //aqui no códio substiruir "R.raw.somcerto" por "R.raw. + nome do som escolhido"
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.suuiii)
        mediaPlayer.start()
    }

    fun Voltar() {
        intent = Intent(this, Jogo::class.java)
        intent.putExtra("pontos",pontos)
        intent.putExtra("nome",nome)
        intent.putExtra("temSom", temSom)
        startActivity(intent)
    }
    fun Voltar(view: View) {
        // Aqui você pode chamar a função Menu que espera um parâmetro view
        Voltar()
    }
}