package com.estebanposada.noteapp.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val title: String,
    val isReminder: Boolean = false,
    val date: String = "00/00/0000"
)