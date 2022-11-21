package com.iker.simon_dice_iker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel():ViewModel() {

    var ronda = 1;
    val livedata_ronda = MutableLiveData<Int>()

    init {
        livedata_ronda.value = ronda
    }

    fun sumarRonda(){
        ronda = ronda +1
        livedata_ronda.setValue(ronda)
    }


}