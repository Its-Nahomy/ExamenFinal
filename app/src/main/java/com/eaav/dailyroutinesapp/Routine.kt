package com.eaav.dailyroutinesapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "routines")
@Parcelize
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
) : Parcelable