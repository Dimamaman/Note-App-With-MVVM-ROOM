package uz.gita.dimanote.presentation.screen.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import uz.gita.dimanote.R
import uz.gita.dimanote.databinding.FragmentHomeBinding
import uz.gita.dimanote.presentation.adapter.HomeAdapter
import uz.gita.dimanote.presentation.screen.home.viewmodel.HomeViewModel
import uz.gita.dimanote.presentation.screen.home.viewmodel.impl.HomeViewModelImpl
import uz.gita.dimanote.util.myApply

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val homeAdapter by lazy { HomeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openAddNoteScreenLiveData.observe(this, openAddNoteObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        addNoteBtn.setOnClickListener {
            viewModel.openAddNoteScreen()
        }

        homeAdapter.setLongClickListener {
            viewModel.showDialog(requireContext(), it.id, it.title)
        }

        homeAdapter.setEditClickListener { note ->
            viewModel.openEditNote(note)
        }

        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                homeAdapter.submitList(it)
                recyclerViewHome.adapter = homeAdapter
            } else {
                homeAdapter.submitList(it)
                recyclerViewHome.adapter = homeAdapter
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openEditNoteScreenLiveData.collect {
                val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(it)
                findNavController().navigate(action)
            }
        }

//        viewModel.openEditNoteScreenLiveData.observe(this@HomeFragment) {
//            val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(it)
//            findNavController().navigate(action)
//        }
    }

    private val openAddNoteObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_homeFragment_to_addNote)
    }
}