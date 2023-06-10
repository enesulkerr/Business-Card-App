package com.example.business_card_app.service

import android.content.Context
import androidx.room.Room
import com.example.business_card_app.configs.AppDatabase

class Db(context: Context){

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "appDataBase"
    ).build();

}