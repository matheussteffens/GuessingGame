package com.example.guessinggame.activities

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.guessinggame.R
import com.example.guessinggame.database.DatabaseHelper


class Recorde : AppCompatActivity() {
    lateinit var dbHelper: DatabaseHelper
    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<String>
    var temSom = true


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorde)

        listView = findViewById<ListView>(R.id.lista)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listView.adapter = adapter
        adapter.clear()
        dbHelper = DatabaseHelper(this)
        LerDados()
    }

    fun LerDados() {
        var posicao = 1
        val dbHelper = DatabaseHelper(this)
        val cursor = dbHelper.getAllRecords()
        while (cursor.moveToNext()) {
            val pontos = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_POINTS))
            val nome = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME))
            val dateTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DATE_TIME))

            // Adicione ao adapter para exibir na ListView
            adapter.add(
                "\n" + posicao + "º Lugar" +
                        "\n" + "Nome: $nome\n" +
                        "Pontos: $pontos\n" +
                        "Data: $dateTime\n "
            )
            posicao++
        }
        cursor.close()
    }
    fun Menu() {
        adapter.clear()
        temSom = intent.getBooleanExtra("temSom", true)
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("temSom", temSom)
        startActivity(intent)
    }

    fun Zerar(view: View) {
        showAlertDialog()
    }
    private fun showAlertDialog(){
        AlertDialog.Builder(this)
            .setMessage("Se clicar em ''SIM'' todas as pontuações serão perdidas??")
            .setTitle("Deseja ZERAR as Pontuações?")
            .setPositiveButton("Sim", DialogInterface.OnClickListener { _, _ ->
                dbHelper.deleteAllRecords()
                recreate()
                Toast.makeText(applicationContext,"Pontuações Zeradas", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Não", DialogInterface.OnClickListener { dialogInterface, i->
                Toast.makeText(applicationContext,"Bom Jogo", Toast.LENGTH_SHORT).show()
            })
            .create()
            .show()
    }
    fun Menu(view: View) {
        // Aqui você pode chamar a função Menu que espera um parâmetro view
        Menu()
    }
}