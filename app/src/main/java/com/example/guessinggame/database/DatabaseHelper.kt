package com.example.guessinggame.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DATABASE_NAME = "GuessingGameDB"
        const val TABLE_NAME = "records"
        const val COL_ID = "id"
        const val COL_NAME = "nome"
        const val COL_POINTS = "pontos"
        const val COL_DATE_TIME = "dateTime"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT, $COL_POINTS INTEGER, $COL_DATE_TIME TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addRecord(nome: String, pontos: Int, dateTime: String): Long {
        val db = this.writableDatabase
        // Verificar o número total de registros na tabela
        val totalRecords = DatabaseUtils.queryNumEntries(db, TABLE_NAME)

        if(totalRecords<20){
            val contentValues = ContentValues()
            contentValues.put(COL_NAME, nome)
            contentValues.put(COL_POINTS, pontos)
            contentValues.put(COL_DATE_TIME, dateTime)
            return db.insert(TABLE_NAME, null, contentValues)
        }else
        if ( pontos > getLowestScore()) {
                deleteLowestScore()
            val contentValues = ContentValues()
            contentValues.put(COL_NAME, nome)
            contentValues.put(COL_POINTS, pontos)
            contentValues.put(COL_DATE_TIME, dateTime)
            return db.insert(TABLE_NAME, null, contentValues)
        }
        return 0
    }

    // Método para obter a pontuação mais baixa
    private fun getLowestScore(): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COL_POINTS), null, null, null, null, "$COL_POINTS ASC", "1")
        val lowestScore = if (cursor.moveToFirst()) cursor.getInt(0) else Int.MAX_VALUE
        cursor.close()
        return lowestScore
    }
    // Método para excluir a pontuação mais baixa
    private fun deleteLowestScore() {
        val db = this.writableDatabase
        db.delete(
            TABLE_NAME,
            "$COL_POINTS = (SELECT $COL_POINTS FROM $TABLE_NAME ORDER BY $COL_POINTS ASC, $COL_DATE_TIME ASC LIMIT 1)" +
                    " AND $COL_DATE_TIME = (SELECT $COL_DATE_TIME FROM $TABLE_NAME ORDER BY $COL_POINTS ASC, $COL_DATE_TIME ASC LIMIT 1)",
            null

        )
    }

    fun getAllRecords(): Cursor {

        val db = this.readableDatabase
        return db.rawQuery(
            "SELECT * FROM $TABLE_NAME ORDER BY $COL_POINTS DESC, $COL_DATE_TIME DESC",
            null)

    }

    fun deleteAllRecords() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}
