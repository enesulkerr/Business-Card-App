package com.example.business_card_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.business_card_app.service.Db

class addActivity : AppCompatActivity() {

    lateinit var ad : EditText
    lateinit var soyad : EditText
    lateinit var yas : EditText
    lateinit var grup : EditText
    lateinit var ekle : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ad = findViewById(R.id.adtextview)
        soyad = findViewById(R.id.soyadtextview)
        yas = findViewById(R.id.yastextview)
        grup = findViewById(R.id.grouptextview)
        ekle = findViewById(R.id.AddBtn)

        val db = Db(this).db

        ekle.setOnClickListener {
            val adText = ad.text.toString()
            val soyadText = soyad.text.toString()
            val yasText = yas.text.toString()
            val grupText = grup.text.toString()
            val note1 = com.example.business_card_app.models.Note(null,adText,soyadText,yasText.toInt(),grupText,null)
            Thread {
                db.noteDao().insert(note1)
                runOnUiThread {
                    onBackPressed()
                }
            }.start()



            onBackPressed()
        }










    }
}