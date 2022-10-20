package com.iker.simon_dice_iker

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.jvm.functions.FunctionN


class MainActivity : AppCompatActivity() {

    private var ronda = 1
    private var numbersArray= IntArray(1000)
    private var contador=0
    private var puntuacion=0
    var numerointroducido:Int = 0

    lateinit var btnRojo:Button
    lateinit var btnAzul:Button
    lateinit var btnVerde:Button
    lateinit var btnAmarillo:Button
    lateinit var btnIniciar:Button
    lateinit var score:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIniciar= findViewById(R.id.btn_Inicio)
        btnRojo=findViewById(R.id.btn_rojo)
        btnAzul=findViewById(R.id.btn_azul)
        btnVerde=findViewById(R.id.btn_verde)
        btnAmarillo=findViewById(R.id.btn_amarillo)
        score = findViewById(R.id.score)
        btnIniciar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                inicio()
                btnIniciar.setVisibility(View.GONE)
                Log.d("Lehasdadoclick","adsf")
            }
        })

    }
    private fun inicio(){
        colorRandom()

    }

    private fun reset(){
        for(i in 0..(ronda-1)){
            numbersArray[i]=0
        }
        score.text = "Score: "
    }

    private fun colorRandom(){

        for(i in 0..ronda){
            val randomValues = Random().nextInt(4) +1
            numbersArray.set((ronda-1),randomValues)
        }

        enterNumbers(numbersArray)
        Log.d("array:", Arrays.toString(numbersArray))



        for (i in 0..numbersArray.size){
            when (numbersArray[i]){
                1-> {
                    btnRojo.setBackgroundColor(R.color.red2)

                }
            }

        }


    }

    private fun enterNumbers(numbersArray: IntArray){
        btnRojo.setOnClickListener(){
            Log.d("botónpresionado:","1")
            numerointroducido=1
            comprobante()
        }
        btnAzul.setOnClickListener(){
            Log.d("botónpresionado:","2")
            numerointroducido=2
            comprobante()
        }
        btnVerde.setOnClickListener(){
            Log.d("botónpresionado:","3")
            numerointroducido=3
            comprobante()
        }
        btnAmarillo.setOnClickListener(){
            Log.d("botónpresionado:","4")
            numerointroducido=4
            comprobante()
        }
    }

    private fun comprobante(){
        if(numerointroducido == numbersArray[contador] && contador != ronda){
            contador+=1

            if(contador==ronda){
                comprobante()
            }
        }else if(contador == ronda || numerointroducido == numbersArray[contador]){
            ronda+=1
            puntuacion+=10
            score.text = "Score: " + puntuacion
            contador=0
            colorRandom()
        }else{
            Log.d("Perdiste wey","malo")
            Toast.makeText(this, "Has hecho " + puntuacion + " puntos!",Toast.LENGTH_LONG).show()
            reset()
            btnIniciar.setVisibility(View.VISIBLE)
            ronda=1
            contador=0
        }
    }









}