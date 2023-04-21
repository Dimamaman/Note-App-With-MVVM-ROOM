package uz.gita.dimanote.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.databinding.ItemLayoutBinding

class HomeAdapter : ListAdapter<NoteData, HomeAdapter.HomeViewHolder>(DIFF_CALL_BACK) {
    private var longClickListener: ((NoteData) -> Unit)? = null
    fun setLongClickListener(block: (NoteData) -> Unit) {
        longClickListener = block
    }

    private var editClickListener: ((NoteData) -> Unit)? = null
    fun setEditClickListener(block: (NoteData) -> Unit) {
        editClickListener = block
    }

    inner class HomeViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.constraint.setOnLongClickListener {
                longClickListener?.invoke(getItem(adapterPosition))
                true
            }
        }

        fun bind(noteData: NoteData) {
            binding.apply {
                textNoteTitle.text = noteData.title
                textNoteContent.text = noteData.content.parseAsHtml()
                textNoteDate.text = noteData.createdAt.toString()

                if (noteData.isPin == 1) {
                    imagePin.visibility = View.VISIBLE
                } else {
                    imagePin.visibility = View.GONE
                }
            }

            binding.constraint.setOnClickListener {
                editClickListener?.invoke(noteData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<NoteData>() {
            override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
                return oldItem == newItem
            }
        }
    }
}