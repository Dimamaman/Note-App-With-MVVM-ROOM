package uz.gita.dimanote.presentation.screen.trash.viewmodel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import uz.gita.dimanote.R
import uz.gita.dimanote.domain.repository.AppRepository
import uz.gita.dimanote.domain.repository.impl.AppRepositoryImpl

class TrashViewModelImpl: ViewModel(), TrashViewModel {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override val notesInTrash = repository.getNotesInTrash()

    override fun deleteNoteById(noteId: Long) {
        repository.deleteNoteById(noteId)
    }

    override fun recover(noteId: Long) {
        repository.recoverNote(noteId)
    }

    override fun showDialog(context: Context, noteId: Long, title: String) {
        val dialog = Dialog(context)

        dialog.setContentView(R.layout.dialog_custom_trash)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val recover: MaterialButton = dialog.findViewById(R.id.recover_btn)

        val completelyDelete: MaterialButton = dialog.findViewById(R.id.delete_btn)

        val titleNote: AppCompatTextView = dialog.findViewById(R.id.title)


        titleNote.text = context.getString(R.string.title,title)

        recover.setOnClickListener {
            repository.recoverNote(noteId)
            dialog.dismiss()
        }

        completelyDelete.setOnClickListener {
            repository.deleteNoteById(noteId)
            dialog.dismiss()
        }

        dialog.create()
        dialog.show()
    }

    override fun deleteNotesFromTrash() {
        repository.deleteNotesFromTrash()
    }
}