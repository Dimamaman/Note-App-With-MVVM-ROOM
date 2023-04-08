package uz.gita.dimanote.presentation.screen.addnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.domain.repository.AppRepository
import uz.gita.dimanote.domain.repository.impl.AppRepositoryImpl

class AddViewModelImpl: ViewModel(),AddViewModel {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override val closeAddNoteScreen = MutableLiveData<Unit>()

    override fun addNote(noteData: NoteData) {
        repository.addNote(noteData)
    }

    override fun closeAddNote() {
        closeAddNoteScreen.value = Unit
    }
}