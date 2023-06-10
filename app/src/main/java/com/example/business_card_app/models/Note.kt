package com.example.business_card_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nid")
    val nid: Int?,
    val isim:String?,
    val soyisim:String?,
    val yas:Int?,
    val group : String?,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image:ByteArray?

)
