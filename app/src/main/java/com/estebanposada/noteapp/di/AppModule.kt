package com.estebanposada.noteapp.di

import android.content.Context
import androidx.room.Room
import com.estebanposada.noteapp.repository.NoteRepository
import com.estebanposada.noteapp.ui.db.NoteDao
import com.estebanposada.noteapp.ui.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(dao: NoteDao) = NoteRepository(dao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "note-db").build()

    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao()
}