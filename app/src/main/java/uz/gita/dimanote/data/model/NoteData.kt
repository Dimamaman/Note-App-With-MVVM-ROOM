package uz.gita.dimanote.data.model

import androidx.room.ColumnInfo
import uz.gita.dimanote.data.source.local.entity.NoteEntity
import java.util.Date

data class NoteData(
    val id: Long = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "on_trash")
    val onTrash: Int = 0
) {
    fun toNoteEntity(): NoteEntity = NoteEntity(id, title, content, createdAt, onTrash)
}
