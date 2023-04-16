package uz.gita.dimanote.presentation.screen.edit.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.domain.repository.AppRepository
import uz.gita.dimanote.domain.repository.impl.AppRepositoryImpl
import uz.gita.dimanote.presentation.screen.edit.viewmodel.EditFragmentViewModel

class EditFragmentViewModelImpl: ViewModel(), EditFragmentViewModel {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override val backToHomeScreen = MutableLiveData<Unit>()

    override fun updateNote(noteData: NoteData) {
        repository.updateNote(noteData)
        backToHomeScreen.value = Unit
    }
}