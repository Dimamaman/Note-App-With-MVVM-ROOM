package uz.gita.dimanote.presentation.screen.trash

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dimanote.R
import uz.gita.dimanote.databinding.FragmentTrashBinding
import uz.gita.dimanote.presentation.adapter.TrashAdapter
import uz.gita.dimanote.presentation.screen.trash.viewmodel.TrashViewModel
import uz.gita.dimanote.presentation.screen.trash.viewmodel.TrashViewModelImpl
import uz.gita.dimanote.util.myApply

class TrashFragment : Fragment(R.layout.fragment_trash) {
    private val viewModel: TrashViewModel by viewModels<TrashViewModelImpl>()
    private val binding by viewBinding(FragmentTrashBinding::bind)
    private val trashAdapter by lazy { TrashAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.trash_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.clear_notes -> {
                        viewModel.deleteNotesFromTrash()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)

        viewModel.notesInTrash.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imageRecyclerBin.visibility = View.VISIBLE
                trashAdapter.submitList(it)
                recyclerViewTrash.adapter = trashAdapter
            } else {
                binding.imageRecyclerBin.visibility = View.GONE
                trashAdapter.submitList(it)
                recyclerViewTrash.adapter = trashAdapter
            }
        }

        trashAdapter.setLongClickListener {
            viewModel.showDialog(requireContext(), it.id, it.title)
        }
    }
}