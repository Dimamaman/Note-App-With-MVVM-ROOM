package uz.gita.dimanote.presentation.screen.addnote.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.domain.repository.AppRepository
import uz.gita.dimanote.domain.repository.impl.AppRepositoryImpl
import uz.gita.dimanote.presentation.screen.addnote.viewmodel.AddViewModel

class AddViewModelImpl: ViewModel(), AddViewModel {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override val closeAddNoteScreen = MutableLiveData<Unit>()

    override fun addNote(noteData: NoteData) {
        repository.addNote(noteData)
    }

    override fun closeAddNote() {
        closeAddNoteScreen.value = Unit
    }
}