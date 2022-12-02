package com.iker.simon_dice_iker.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iker.simon_dice_iker.Record

@Database(entities = [Record::class], version = 1, exportSchema = false)
abstract class SmDB : RoomDatabase(){

    abstract fun DAORecord():DAORecord


    companion object {
        @Volatile
        private var INSTANCE: SmDB? = null

        fun getDatabase(context: Context): SmDB {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SmDB::class.java,
                        "myDatabase.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }
    }

}