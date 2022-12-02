package com.iker.simon_dice_iker.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.iker.simon_dice_iker.Record

@Dao
interface DAORecord{
    @Query("SELECT * FROM Record")
    fun getAll(): List<Record>

    @Query("INSERT INTO Record(Usuario,puntuacion) values(:usuario,:puntuacion)")
    fun insertUsuario(usuario: String, puntuacion: Int)

    @Insert
    fun insertAll(vararg users: Record)

    @Delete
    fun delete(user: Record)
}