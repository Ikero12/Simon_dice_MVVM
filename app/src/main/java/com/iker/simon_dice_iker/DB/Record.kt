package com.iker.simon_dice_iker

import androidx.room.*;

@Entity(tableName = "Record")
data class Record (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo val Usuario:String?,
    @ColumnInfo val puntuacion:Int
        )

