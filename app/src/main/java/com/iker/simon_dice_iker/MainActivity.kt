package com.iker.simon_dice_iker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Random


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnIniciar: Button = findViewById(R.id.btn_Inicio)
        val btnRojo:Button = findViewById(R.id.btn_rojo)
        val btnAzul:Button = findViewById(R.id.btn_azul)
        val btnVerde:Button = findViewById(R.id.btn_verde)
        val btnAmarillo:Button = findViewById(R.id.btn_amarillo)



        btnIniciar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                inicio()
                colorRandom()
                btnIniciar.setVisibility(View.GONE)
                Log.d("Lehasdadoclick","adsf")
            }
        })


    }
    private var ronda = 1
    private var numbersArray= IntArray(ronda)
    private var numbersIntroduced=IntArray(ronda)
    private fun colorRandom(){

        for(i in 1..ronda){
            val randomValues = Random().nextInt(4) +1
            numbersArray.set(ronda,randomValues)
        }


        enterNumbers(numbersArray)
    }

    private fun enterNumbers(numbersArray: IntArray){




    }

    private fun inicio(){
        colorRandom()
    }


}