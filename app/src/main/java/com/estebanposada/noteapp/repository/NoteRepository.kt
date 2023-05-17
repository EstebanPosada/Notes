package com.estebanposada.noteapp.repository

import com.estebanposada.noteapp.ui.db.NoteDao
import com.estebanposada.noteapp.ui.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {

    suspend fun saveNote(note: Note) = dao.insert(note)

    suspend fun getNotes() = dao.getNotes()

    suspend fun getReminders() = dao.getReminders()

    suspend fun update(note: Note) = dao.update(note)

    suspend fun delete(id: String) = dao.deleteById(id)

    suspend fun getNote(id: String) = dao.getNote(id)
}