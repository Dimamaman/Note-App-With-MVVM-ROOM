package uz.gita.dimanote.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize
import uz.gita.dimanote.data.source.local.entity.NoteEntity
import java.util.Date
@Parcelize
data class NoteData(
    val id: Long = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "on_trash")
    val onTrash: Int = 0
): Parcelable {
    fun toNoteEntity(): NoteEntity = NoteEntity(id, title, content, createdAt, onTrash)
}
