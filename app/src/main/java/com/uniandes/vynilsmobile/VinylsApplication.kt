package com.uniandes.vynilsmobile

import android.app.Application
import com.uniandes.vynilsmobile.data.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}