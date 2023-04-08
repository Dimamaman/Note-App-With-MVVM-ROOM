package uz.gita.dimanote.presentation.screen.trash.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.dimanote.data.model.NoteData

interface TrashViewModel {
    val notesInTrash: LiveData<List<NoteData>>
    fun deleteNoteById(noteId: Long)
    fun recover(noteId: Long)
    fun showDialog(context: Context, noteId: Long,title: String)
    fun deleteNotesFromTrash()
}