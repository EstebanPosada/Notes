package com.estebanposada.noteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebanposada.noteapp.repository.NoteRepository
import com.estebanposada.noteapp.ui.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _noteData = MutableLiveData<List<Note>>()
    val noteData: LiveData<List<Note>>
        get() = _noteData

    private val _reminderData = MutableLiveData<List<Note>>()
    val reminderData: LiveData<List<Note>>
        get() = _reminderData


    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    fun saveNote(note: String, isReminder: Boolean, date: String) = viewModelScope.launch {
        repository.saveNote(Note(title = note, isReminder = isReminder, date = date))
    }

    fun getNote(id: String) = viewModelScope.launch {
        val note = repository.getNote(id)
        _note.postValue(note)
    }

    fun editNote(id: String, note: String, isReminder: Boolean) = viewModelScope.launch {
        repository.update(Note(uid = id, title = note, isReminder = isReminder))
    }

    fun getNotes() = viewModelScope.launch {
        val notes = repository.getNotes()
        _noteData.postValue(notes)
    }

    fun getReminders() = viewModelScope.launch {
        val reminders = repository.getReminders()
        _reminderData.postValue(reminders)
    }

    fun delete(id: String) = viewModelScope.launch {
        repository.delete(id)
    }
}