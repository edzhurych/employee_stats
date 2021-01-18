package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Employee::class, Specialty::class, EmployeeToSpecialty::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    internal abstract fun mainDao(): MainDao
}