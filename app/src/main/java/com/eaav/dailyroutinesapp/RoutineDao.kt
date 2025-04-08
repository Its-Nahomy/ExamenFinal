package com.eaav.dailyroutinesapp
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routines")
    suspend fun getAll(): List<Routine>

    @Insert
    suspend fun insert(routine: Routine)
}