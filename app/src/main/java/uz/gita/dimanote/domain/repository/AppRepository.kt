package uz.gita.dimanote.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.presentation.adapter.data.RichFeatureModel

interface AppRepository {
    fun addNote(noteData: NoteData)

    fun updateNote(noteData: NoteData)

    fun deleteNote(noteData: NoteData)

    fun deleteNotes(vararg notes: NoteData)

    fun getNotes(): LiveData<List<NoteData>>

    fun getNotesInTrash(): LiveData<List<NoteData>>

    fun noteToTrash(id: Long)

    fun deleteNoteById(noteId: Long)

    fun recoverNote(noteId: Long)

    fun deleteNotesFromTrash()

    fun search(search: String): List<NoteData>

    fun pinNote(noteId: Long)

    fun unPinNote(noteId: Long)

    fun getRichFeaturesData(): List<RichFeatureModel>
}