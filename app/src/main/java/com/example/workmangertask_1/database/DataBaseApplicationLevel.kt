package com.example.workmangertask_1.database

import android.app.Application
import android.content.Context
import com.example.services.ItemRoomDatabase

class DataBaseApplicationLevel(context: Context) : Application() {
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(context = context) }
}