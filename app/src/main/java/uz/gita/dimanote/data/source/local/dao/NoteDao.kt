package uz.gita.dimanote.data.source.local.dao

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.data.source.local.entity.NoteEntity

@Dao
interface NoteDao {

    @Insert
    fun insert(note: NoteEntity)

    @Update
    fun update(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

    @Delete
    fun deleteNotes(vararg notes: NoteEntity)

    @Query("SELECT * FROM Notes WHERE on_trash=0")
    fun getNotes(): LiveData<List<NoteData>>

    @Query("SELECT * FROM Notes WHERE on_trash=1")
    fun getNotesInTrash(): LiveData<List<NoteData>>

    @Query("UPDATE Notes SET on_trash=1 WHERE id= :noteId")
    fun noteToTrash(noteId: Long)

    @Query("UPDATE Notes SET on_trash=0 WHERE id= :noteId")
    fun recoverNote(noteId: Long)

    @Query("DELETE FROM Notes WHERE id= :noteId")
    fun deleteNoteById(noteId: Long)

    @Query("DELETE FROM Notes WHERE on_trash=1")
    fun deleteNotesFromTrash()

    @Query("SELECT * FROM Notes WHERE title LIKE '%' || :search || '%' AND on_trash=0")
    fun search(search: String): List<NoteData>
}