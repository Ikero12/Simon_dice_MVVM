package com.iker.simon_dice_iker


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.*
import androidx.lifecycle.Observer
import androidx.room.Room
import com.iker.simon_dice_iker.DB.DAORecord
import com.iker.simon_dice_iker.DB.SmDB


class MainActivity : AppCompatActivity() {
    //regionvariables
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
    lateinit var record:TextView
    lateinit var rondaview:TextView
    lateinit var daoRecord: DAORecord

    var puntuacion2: Int=0
    val mimodelo by viewModels<MyViewModel>()

    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIniciar= findViewById(R.id.btn_Inicio)
        btnRojo=findViewById(R.id.btn_rojo)
        btnAzul=findViewById(R.id.btn_azul)
        btnVerde=findViewById(R.id.btn_verde)
        btnAmarillo=findViewById(R.id.btn_amarillo)
        score = findViewById(R.id.score)
        record = findViewById(R.id.record)
        rondaview = findViewById(R.id.ronda)
        loadRecord()
        btnIniciar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                inicio()
                btnIniciar.setVisibility(View.GONE)
                Log.d("Lehasdadoclick","adsf")
            }
        })

        daoRecord = SmDB.getDatabase(this).DAORecord()

        mimodelo.livedata_ronda.observe(this, Observer {
            fun(nuevaRonda: Int){
                rondaview = findViewById(R.id.ronda)
                rondaview.text = "Ronda: " + nuevaRonda
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


        jobColores()
    }


    private fun jobColores(){
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
            mimodelo.sumarRonda()
            rondaview.text="Ronda: " + ronda
            puntuacion+=10
            score.text = "Score: " + puntuacion
            contador=0
            colorRandom()
        }else{
            Toast.makeText(this, "Has hecho " + puntuacion + " puntos!",Toast.LENGTH_LONG).show()
            reset()
            saveRecord()
            loadRecord()
            btnIniciar.setVisibility(View.VISIBLE)
            puntuacion=0
            ronda=1
            contador=0
            rondaview.text="Ronda: "
        }
    }



    fun activarBotones(boolean: Boolean){
        btnRojo.isClickable == boolean
        btnAzul.isClickable == boolean
        btnVerde.isClickable == boolean
        btnAmarillo.isClickable == boolean

    }

    private fun saveRecord(){
     if(puntuacion>puntuacion2){
         puntuacion2 = puntuacion
         val sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE)
         val editor = sharedPreferences.edit()
         editor.apply(){
             putInt("INT_KEY",puntuacion2)
         }.apply()
     }
     Toast.makeText(this,"Se ha establecido un nuevo record!",Toast.LENGTH_LONG)
 }
    private fun loadRecord(){
        val sharedPreferences = getSharedPreferences("record", Context.MODE_PRIVATE)
        val savedRecord = sharedPreferences.getInt("INT_KEY",0)
        record.text = "Record: " + savedRecord
    }

    private fun askUser(){
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("User Record")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK"){ dialogInterface, i -> Toast.makeText(applicationContext, "El usuario es: " + editText.text.toString(), Toast.LENGTH_SHORT).show() }
        builder.show()
    }

}