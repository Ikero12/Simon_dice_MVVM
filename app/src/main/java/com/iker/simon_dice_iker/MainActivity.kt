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
import kotlinx.coroutines.*
import java.util.*
import kotlin.jvm.functions.FunctionN


class MainActivity : AppCompatActivity() {

    private var ronda = 1
    private var numbersArray= IntArray(1000)
    private var contador=0
    private var puntuacion=0
    private var booleanBotones: Boolean = true
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

    /*
    Método que resetea el juego y el score.
     */
    private fun reset(){
        for(i in 0..(ronda-1)){
            numbersArray[i]=0
        }
        score.text = "Score: "
    }

    /*
    Método que saca la secuencia de colores y que además hace que se iluminen
     */
    private fun colorRandom(){
        booleanBotones=false
        activarBotones(booleanBotones)

        for(i in 0..ronda){
            val randomValues = Random().nextInt(4) +1
            numbersArray.set((ronda-1),randomValues)
        }
        enterNumbers(numbersArray)
        Log.d("array:", Arrays.toString(numbersArray)) //Para comprobar la secuencia de colores de la máquina mediante la consola

            val jobColores = GlobalScope.launch(Dispatchers.Main) {

                for (i in 0..ronda){
                    when (numbersArray[i]){
                        1-> {
                            btnRojo.setBackgroundColor(resources.getColor(R.color.red2))
                            delay(750)
                            btnRojo.setBackgroundColor(resources.getColor(R.color.red))
                            delay(100)
                        }
                        2->{
                            btnAzul.setBackgroundColor(resources.getColor(R.color.blue2))
                            delay(750)
                            btnAzul.setBackgroundColor(resources.getColor(R.color.blue))
                            delay(100)
                        }
                        3->{
                            btnVerde.setBackgroundColor(resources.getColor(R.color.green2))
                            delay(750)
                            btnVerde.setBackgroundColor(resources.getColor(R.color.green))
                            delay(100)
                        }
                        4->{
                            btnAmarillo.setBackgroundColor(resources.getColor(R.color.yellow2))
                            delay(750)
                            btnAmarillo.setBackgroundColor(resources.getColor(R.color.yellow))
                            delay(100)
                        }
                    }

                }
                booleanBotones=true
                activarBotones(booleanBotones)
            }

        jobColores

    }



    private fun enterNumbers(numbersArray: IntArray){
        btnRojo.setOnClickListener(){

            if(booleanBotones){
                Log.d("botónpresionado:","1")
                numerointroducido=1
                comprobante()
            }
        }
        btnAzul.setOnClickListener(){

            if(booleanBotones){
                Log.d("botónpresionado:","2")
                numerointroducido=2
                comprobante()
            }

        }
        btnVerde.setOnClickListener(){

            if(booleanBotones){
                Log.d("botónpresionado:","3")
                numerointroducido=3
                comprobante()
            }

        }
        btnAmarillo.setOnClickListener(){

            if(booleanBotones){
                Log.d("botónpresionado:","4")
                numerointroducido=4
                comprobante()
            }
        }
    }

    /*
    Método que comprueba que la secuencia introducida por el usuario es la correcta.
    En caso de que la secuencia sea la correcta le da una nueva manteniendo la antigua y en caso de que no
    le salta un mensaje de que ha perdido con su puntuación.
     */
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
            puntuacion=0
            ronda=1
            contador=0
        }
    }



    fun activarBotones(boolean: Boolean){
        btnRojo.isClickable == boolean
        btnAzul.isClickable == boolean
        btnVerde.isClickable == boolean
        btnAmarillo.isClickable == boolean

    }






}