package com.example.guessinggame.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.guessinggame.R


class MainActivity : AppCompatActivity() {
    lateinit var som : ImageButton
    lateinit var mudo : ImageButton
    var temSom = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        som = findViewById<ImageButton>(R.id.som)
        mudo = findViewById<ImageButton>(R.id.mudo)
        ConferirSom()

    }
    fun ConferirSom(){
        if(intent.hasExtra("temSom")){
            temSom = intent.getBooleanExtra("temSom",true)
            if(temSom) {
                som.visibility = View.VISIBLE
                mudo.visibility = View.INVISIBLE
            }
            else{
                som.visibility = View.INVISIBLE
                mudo.visibility = View.VISIBLE
            }
        }
        else{
            som.visibility = View.VISIBLE
            mudo.visibility = View.INVISIBLE
        }
        if(intent.hasExtra("temSom")) {
            intent = Intent(this, Nome::class.java)
            intent.putExtra("temSom", temSom)
        }
        else{
            intent = Intent(this, Nome::class.java)
            intent.putExtra("temSom", true)
        }
    }

    fun Mudo(view: View) {

        som.visibility = View.VISIBLE
        mudo.visibility = View.INVISIBLE
        intent.putExtra("temSom",true)

    }
    fun Som(view: View) {

        mudo.visibility = View.VISIBLE
        som.visibility = View.INVISIBLE
        intent.putExtra("temSom",false)


    }

    fun Record(view: View) {
        if(intent.hasExtra("temSom")) {
            temSom = intent.getBooleanExtra("temSom",true)
            intent = Intent(this, Recorde::class.java)
            intent.putExtra("temSom", temSom)
        }
        else{
            intent = Intent(this, Recorde::class.java)
            intent.putExtra("temSom", true)
        }
        startActivity(intent)
    }
    fun Novo(view: View) {
        startActivity(intent)
    }

    fun Sobre(view: View) {
        if(intent.hasExtra("temSom")) {
            temSom = intent.getBooleanExtra("temSom",true)
            intent = Intent(this, SobreAcvtivity::class.java)
            intent.putExtra("temSom", temSom)
        }
        else{
            intent = Intent(this, SobreAcvtivity::class.java)
            intent.putExtra("temSom", true)
        }
        startActivity(intent)}
    override fun onBackPressed() {
        // não chame o super desse método
    }
}