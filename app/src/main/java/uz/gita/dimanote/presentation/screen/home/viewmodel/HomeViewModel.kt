package uz.gita.dimanote.presentation.screen.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.dimanote.data.model.NoteData

interface HomeViewModel {
    val notesLiveData: LiveData<List<NoteData>>
    val openAddNoteScreenLiveData: SharedFlow<Unit>
    val openEditNoteScreenLiveData: SharedFlow<NoteData>
    val searchNotesLiveData: LiveData<List<NoteData>>

    fun openAddNoteScreen()
    fun showDialog(requireContext: Context, noteId: Long, title: String)
    fun openEditNote(noteData: NoteData)

    fun searchNote(search: String)
    fun getAllNotes()

    fun updateNote(noteData: NoteData)

    fun pinNote(noteId: Long)

    fun unPinNote(noteId: Long)
}