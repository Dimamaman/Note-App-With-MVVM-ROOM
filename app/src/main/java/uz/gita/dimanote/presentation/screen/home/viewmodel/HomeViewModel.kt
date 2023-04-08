package uz.gita.dimanote.presentation.screen.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.dimanote.data.model.NoteData

interface HomeViewModel {
    val notesLiveData: LiveData<List<NoteData>>
    val openAddNoteScreenLiveData: LiveData<Unit>

    fun openAddNoteScreen()
    fun showDialog(requireContext: Context, noteId: Long, title: String)
}