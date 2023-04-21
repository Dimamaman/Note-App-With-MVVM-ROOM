package uz.gita.dimanote.presentation.screen.edit.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.presentation.adapter.data.RichFeatureModel

interface EditFragmentViewModel {
    val backToHomeScreen: LiveData<Unit>
    fun updateNote(noteData: NoteData)
    fun getRichFeatures() : List<RichFeatureModel>
}