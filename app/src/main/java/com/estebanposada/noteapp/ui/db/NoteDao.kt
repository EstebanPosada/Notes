package com.estebanposada.noteapp.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estebanposada.noteapp.ui.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE isReminder = 0")
    suspend fun getNotes(): List<Note>

    @Query("SELECT * FROM note WHERE isReminder = 1")
    suspend fun getReminders(): List<Note>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM note WHERE uid = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM note")
    suspend fun deleteAll()

    @Query("SELECT * FROM note WHERE uid = :id")
    suspend fun getNote(id: String): Note
}