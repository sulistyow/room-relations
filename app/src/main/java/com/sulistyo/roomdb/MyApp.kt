package com.sulistyo.roomdb

import android.app.Application
import com.sulistyo.roomdb.database.StudentDatabase

class MyApp : Application() {
    val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}