package com.iker.simon_dice_iker

import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.jvm.functions.FunctionN


class MainActivity : AppCompatActivity() {

    private var ronda = 1
    private var numbersArray= IntArray(1000)
    private var contador=0
    var numerointroducido:Int = 0

    lateinit var btnRojo:Button
    lateinit var btnAzul:Button
    lateinit var btnVerde:Button
    lateinit var btnAmarillo:Button
    lateinit var btnIniciar:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIniciar= findViewById(R.id.btn_Inicio)
        btnRojo=findViewById(R.id.btn_rojo)
        btnAzul=findViewById(R.id.btn_azul)
        btnVerde=findViewById(R.id.btn_verde)
        btnAmarillo=findViewById(R.id.btn_amarillo)
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

    }

    private fun colorRandom(){

        for(i in 0..ronda){
            val randomValues = Random().nextInt(4) +1
            numbersArray.set((ronda-1),randomValues)
        }

        enterNumbers(numbersArray)
        Log.d("array:", Arrays.toString(numbersArray))

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
            contador=0
            colorRandom()
        }else{
            Log.d("Perdiste wey","malo")
            reset()
            btnIniciar.setVisibility(View.VISIBLE)
            ronda=1
            contador=0
        }
    }









}