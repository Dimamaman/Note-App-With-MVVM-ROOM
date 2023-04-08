package uz.gita.dimanote.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.data.source.local.NoteDatabase
import uz.gita.dimanote.domain.repository.AppRepository

class AppRepositoryImpl private constructor() : AppRepository {

    companion object {
        private lateinit var repository: AppRepositoryImpl

        fun getInstance(): AppRepositoryImpl {
            if (!(::repository.isInitialized)) {
                repository = AppRepositoryImpl()
            }
            return repository
        }
    }

    private val noteDao = NoteDatabase.getInstance().getNoteDao()

    override fun addNote(noteData: NoteData) {
        noteDao.insert(noteData.toNoteEntity())
    }

    override fun updateNote(noteData: NoteData) {
        noteDao.update(noteData.toNoteEntity())
    }

    override fun deleteNote(noteData: NoteData) {
        noteDao.delete(noteData.toNoteEntity())
    }

    override fun deleteNotes(vararg notes: NoteData) {
        val list = notes.map {
            it.toNoteEntity()
        }.toTypedArray()

        noteDao.deleteNotes(*list)
    }

    override fun getNotes(): LiveData<List<NoteData>> {
        return noteDao.getNotes()

//        val data = noteDao.getNotes()
//        return Transformations.map(data) {
//            val mappedList = mutableListOf<NoteData>()
//            it.forEach {
//                mappedList.add(it.toNoteData())
//            }
//            return@map mappedList
    }

    override fun getNotesInTrash(): LiveData<List<NoteData>> {
        return noteDao.getNotesInTrash()

        /*val data = noteDao.getNotesInTrash()
    return Transformations.map(data) {
        val mappedList = mutableListOf<NoteData>()
        it.forEach {
            mappedList.add(it.toNoteData())
        }
        return@map mappedList
    }*/
    }

    override fun noteToTrash(noteId: Long) {
        noteDao.noteToTrash(noteId)
    }

    override fun deleteNoteById(noteId: Long) {
        noteDao.deleteNoteById(noteId)
    }

    override fun recoverNote(noteId: Long) {
        noteDao.recoverNote(noteId)
    }

    override fun deleteNotesFromTrash() {
        noteDao.deleteNotesFromTrash()
    }
}