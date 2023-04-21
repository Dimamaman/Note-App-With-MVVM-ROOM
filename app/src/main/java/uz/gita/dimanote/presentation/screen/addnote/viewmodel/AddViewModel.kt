package uz.gita.dimanote.presentation.screen.addnote.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.presentation.adapter.data.RichFeatureModel

interface AddViewModel {
    val closeAddNoteScreen: LiveData<Unit>
    fun addNote(noteData: NoteData)
    fun closeAddNote()

    fun getRichFeatures() : List<RichFeatureModel>
}