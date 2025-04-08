package com.eaav.dailyroutinesapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Routine::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
}