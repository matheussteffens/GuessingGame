package com.example.guessinggame.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.guessinggame.R

class Jogo : AppCompatActivity() {
    lateinit var som : ImageButton
    lateinit var mudo : ImageButton
    lateinit var imagemtopo : ImageView
    lateinit var txtPergunta : TextView
    lateinit var txtA : TextView
    lateinit var txtB : TextView
    lateinit var txtC : TextView
    lateinit var txtD : TextView
    var palpite = 'p'
    var acertou = true
    var pontos = 0
    var total = 12 //Numero total de perguntas, imagens, alternativa e respostas  certas
    //Esse numero nunca pode ser maior que o numero  total de perguntas,
    // imagens, alternativa e respostas  certas
    var temSom = true
    var nome = ""

    val imagensList = mutableListOf<Int>()
    var perguntas = emptyArray<String>()
    var opcaoA = emptyArray<String>()
    var opcaoB = emptyArray<String>()
    var opcaoC = emptyArray<String>()
    var opcaoD = emptyArray<String>()
    var certa = emptyArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)


        if(intent.hasExtra("pontos")) {
            pontos = intent.getIntExtra("pontos",0)
            nome = intent.getStringExtra("nome").toString()
        }
        ConferirSom()

        perguntas = resources.getStringArray(R.array.perguntas)
        opcaoA = resources.getStringArray(R.array.opcaoA)
        opcaoB = resources.getStringArray(R.array.opcaoB)
        opcaoC = resources.getStringArray(R.array.opcaoC)
        opcaoD = resources.getStringArray(R.array.opcaoD)
        certa = resources.getStringArray(R.array.certa)


        val imagensArray = resources.obtainTypedArray(R.array.idImagens)
        for (i in 0 until imagensArray.length()) {
            imagensList.add(imagensArray.getResourceId(i, 0))
        }
        imagensArray.recycle()

        imagemtopo = findViewById(R.id.imageView2)
        txtPergunta = findViewById(R.id.Pergunta)
        txtA = findViewById(R.id.ResA)
        txtB = findViewById(R.id.ResB)
        txtC = findViewById(R.id.ResC)
        txtD = findViewById(R.id.ResD)

        CriarPergutna()

    }

    override fun onBackPressed() {
        // não chame o super desse método
        showAlertDialog()
    }
    fun ConferirSom(){
        temSom = intent.getBooleanExtra("temSom",true)
        nome = intent.getStringExtra("nome").toString()
        som = findViewById<ImageButton>(R.id.som)
        mudo = findViewById<ImageButton>(R.id.mudo)
        if(temSom) {
            som.visibility = View.VISIBLE
            mudo.visibility = View.INVISIBLE
        }
        else{
            som.visibility = View.INVISIBLE
            mudo.visibility = View.VISIBLE
        }
    }
    fun CriarPergutna(){
        imagemtopo.setImageResource(imagensList[pontos])
        txtPergunta.text = (pontos+1).toString() +") "+ perguntas[pontos]
        txtA.text = opcaoA[pontos]
        txtB.text = opcaoB[pontos]
        txtC.text = opcaoC[pontos]
        txtD.text = opcaoD[pontos]
    }
    fun Confere(){
        acertou = palpite.toString()==certa[pontos]
        if(acertou){
            pontos++
            if(pontos!=total) {
                intent = Intent(this, Acertou::class.java)
                intent.putExtra("temSom",temSom)
                intent.putExtra("nome",nome)
                intent.putExtra("pontos",pontos)
                startActivity(intent)
            }
            else {
                intent = Intent(this, Terminou::class.java)
                intent.putExtra("temSom", temSom)
                intent.putExtra("nome",nome)
                intent.putExtra("pontos",pontos)
                startActivity(intent)
            }
        }
        else {
            intent = Intent(this, Errou::class.java)
            intent.putExtra("temSom", temSom)
            intent.putExtra("nome",nome)
            intent.putExtra("pontos",pontos)
            startActivity(intent)
        }
    }

    fun Mudo(view: View) {


        som.visibility = View.VISIBLE
        mudo.visibility = View.INVISIBLE
        temSom = true

       // intent.putExtra("temSom",true)
    }
    fun Som(view: View) {

            mudo.visibility = View.VISIBLE
            som.visibility = View.INVISIBLE
            temSom = false

       // intent.putExtra("temSom",false)
    }
    fun A(view: View) {
        palpite = 'a'
        Confere()
    }
    fun B(view: View) {
        palpite = 'b'
        Confere()
    }
    fun C(view: View) {
        palpite = 'c'
        Confere()
    }
    fun D(view: View) {
        palpite = 'd'
        Confere()
        // imagemtopo.setImageResource(R.drawable.giffundo)

    }

    fun Menu(view: View) {
        showAlertDialog()
    }
    private fun showAlertDialog(){
        AlertDialog.Builder(this)
            .setMessage("Se clicar em ''Sim'', perderá seu progresso atual")
            .setTitle("Deseja ir ao menu?")
            .setPositiveButton("Sim", DialogInterface.OnClickListener { _, _ ->
               IrMenu()
            })
            .setNegativeButton("Não", DialogInterface.OnClickListener {dialogInterface, i->
                Toast.makeText(applicationContext,"Bom Jogo", Toast.LENGTH_SHORT).show()
            })
            .create()
            .show()
    }
    fun IrMenu(){
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("temSom", temSom)
        startActivity(intent)
    }
}

