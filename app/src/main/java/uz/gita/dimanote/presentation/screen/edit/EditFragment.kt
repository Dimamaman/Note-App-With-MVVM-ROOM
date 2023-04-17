package uz.gita.dimanote.presentation.screen.edit

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dimanote.R
import uz.gita.dimanote.data.model.NoteData
import uz.gita.dimanote.data.source.local.converter.DataConverter
import uz.gita.dimanote.databinding.FragmentEditBinding
import uz.gita.dimanote.presentation.screen.edit.viewmodel.EditFragmentViewModel
import uz.gita.dimanote.presentation.screen.edit.viewmodel.impl.EditFragmentViewModelImpl

class EditFragment : Fragment(R.layout.fragment_edit) {
    private val binding by viewBinding<FragmentEditBinding>()
    private val viewModel: EditFragmentViewModel by viewModels<EditFragmentViewModelImpl>()
    private val args: EditFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        etTitle.editText?.setText(args.note.title)
        etContent.editText?.setText(args.note.content)

        binding.editNoteBtn.setOnClickListener {
            if (etTitle.editText?.text.toString().isEmpty() || etContent.editText?.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fieldlarni to'ldiring", Toast.LENGTH_SHORT).show()
            } else {
                val title = binding.etTitle.editText?.text.toString().trim()
                val content = binding.etContent.editText?.text.toString().trim()
                val time = DataConverter.getCurrentTime()
                viewModel.updateNote(NoteData(id = args.note.id, title = title, content = content, createdAt = time))
                Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.backToHomeScreen.observe(viewLifecycleOwner, backToHomeScreenObserve)
    }

    private val backToHomeScreenObserve = Observer<Unit> {
        findNavController().navigateUp()
    }
}