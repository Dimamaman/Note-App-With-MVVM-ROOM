package uz.gita.dimanote.presentation.screen.edit.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.dimanote.data.model.NoteData

interface EditFragmentViewModel {
    val backToHomeScreen: LiveData<Unit>
    fun updateNote(noteData: NoteData)
}