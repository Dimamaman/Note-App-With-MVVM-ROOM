package uz.gita.dimanote.presentation.screen.addnote

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dimanote.R
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.data.source.local.converter.DataConverter
import uz.gita.dimanote.databinding.FragmentAddBinding
import uz.gita.dimanote.presentation.screen.addnote.viewmodel.AddViewModel
import uz.gita.dimanote.presentation.screen.addnote.viewmodel.AddViewModelImpl
import uz.gita.dimanote.util.myApply

class AddNote : Fragment(R.layout.fragment_add) {
    private val viewModel: AddViewModel by viewModels<AddViewModelImpl>()
    private val binding by viewBinding(FragmentAddBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.closeAddNoteScreen.observe(this,closeAddNoteObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        addNoteBtn.setOnClickListener {
//            if (TextUtils.isEmpty(etTitle.text.toString()) || TextUtils.isEmpty(etContent.text.toString())) {
//                Toast.makeText(requireContext(), "Field is empty", Toast.LENGTH_SHORT).show()
//            } else {
                val title = etTitle.text.toString()
                val content = etContent.text.toString()
                val time = DataConverter.getCurrentTime()
                viewModel.addNote(NoteData(title = title, content = content, createdAt = time))
                viewModel.closeAddNote()
//            }
        }
    }

    private val closeAddNoteObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_addNote_to_homeFragment)
    }
}