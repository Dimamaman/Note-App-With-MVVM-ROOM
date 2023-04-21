package uz.gita.dimanote.presentation.screen.edit

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
import uz.gita.dimanote.presentation.adapter.data.RichFeatureType
import uz.gita.dimanote.presentation.adapter.RichFeatureAdapter
import uz.gita.dimanote.presentation.screen.edit.viewmodel.EditFragmentViewModel
import uz.gita.dimanote.presentation.screen.edit.viewmodel.impl.EditFragmentViewModelImpl

class EditFragment : Fragment(R.layout.fragment_edit) {
    private val binding by viewBinding<FragmentEditBinding>()
    private val viewModel: EditFragmentViewModel by viewModels<EditFragmentViewModelImpl>()
    private val args: EditFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        etTitle.editText?.setText(args.note.title)
        richEditor.html = args.note.content
        init()
        setListener()

        richEditor.setPadding(20, 10, 20, 10)

        context?.let { ContextCompat.getColor(it, R.color.window_background) }
            ?.let { richEditor.setEditorBackgroundColor(it) }

        context?.let { ContextCompat.getColor(it, R.color.noteItemTextColor) }
            ?.let { richEditor.setEditorFontColor(it) }

        binding.editNoteBtn.setOnClickListener {
            if (etTitle.editText?.text.toString().isEmpty() || etContent.editText?.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Fieldlarni to'ldiring", Toast.LENGTH_SHORT).show()
            } else {
                val title = binding.etTitle.editText?.text.toString().trim()
                val content = binding.richEditor.html.toString().trim()
                val time = DataConverter.getCurrentTime()
                viewModel.updateNote(NoteData(id = args.note.id, title = title, content = content, createdAt = time))
                Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.backToHomeScreen.observe(viewLifecycleOwner, backToHomeScreenObserve)
    }

    private var adapter: RichFeatureAdapter? = null
    private fun init() {
        val data = viewModel.getRichFeatures()
        adapter = RichFeatureAdapter()
        binding.rvRich.adapter = adapter
        adapter?.submitList(data)
    }

    private fun setListener() {
        adapter?.setSelectListener { type ->
            when (type) {
                RichFeatureType.BOLD -> binding.richEditor.setBold()
                RichFeatureType.ITALIC -> binding.richEditor.setItalic()
                RichFeatureType.SUBSCRIPT -> binding.richEditor.setSubscript()
                RichFeatureType.SUPERSCRIPT -> binding.richEditor.setSuperscript()
                RichFeatureType.STRIKETHROUGH -> binding.richEditor.setStrikeThrough()
                RichFeatureType.UNDERLINE -> binding.richEditor.setUnderline()
                RichFeatureType.H1 -> binding.richEditor.setHeading(1)
                RichFeatureType.H2 -> binding.richEditor.setHeading(2)
                RichFeatureType.H3 -> binding.richEditor.setHeading(3)
                RichFeatureType.H4 -> binding.richEditor.setHeading(4)
                RichFeatureType.H5 -> binding.richEditor.setHeading(5)
                RichFeatureType.H6 -> binding.richEditor.setHeading(6)
                RichFeatureType.JUSTIFYCENTER -> binding.richEditor.setAlignCenter()
                RichFeatureType.JUSTIFYLEFT -> binding.richEditor.setAlignLeft()
                RichFeatureType.JUSTIFYRIGHT -> binding.richEditor.setAlignRight()
                else -> { }
            }
        }

        binding.richEditor.setOnFocusChangeListener { view, focused ->
            binding.rvRich.isVisible = focused
        }
    }

    private val backToHomeScreenObserve = Observer<Unit> {
        findNavController().navigateUp()
    }
}