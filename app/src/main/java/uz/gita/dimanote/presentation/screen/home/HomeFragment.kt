package uz.gita.dimanote.presentation.screen.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        requireActivity().addMenuProvider(object : MenuProvider {
            @SuppressLint("DiscouragedApi")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val search = menu.findItem(R.id.appSearchBar)
                search.setIcon(R.drawable.ic_search)
                val searchView = search.actionView as SearchView

                val searchPlateId = searchView.context.resources.getIdentifier(
                    "android:id/search_plate",
                    null,
                    null
                )
                val searchPlate: View = searchView.findViewById(searchPlateId)
                searchPlate.setBackgroundResource(0)
                searchView.isIconifiedByDefault = false

                val magId = searchView.resources.getIdentifier("android:id/search_mag_icon", null, null)
                val magImage: ImageView = searchView.findViewById<View>(magId) as ImageView

                val linearLayoutSearchView = magImage.parent as ViewGroup
                linearLayoutSearchView.removeView(magImage)

                searchView.onActionViewExpanded()
                searchView.queryHint = "Search..."

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.searchNote(search = it) }

                        if (newText!!.isEmpty()) {
                            viewModel.getAllNotes()
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner)


        addNoteBtn.setOnClickListener {
            Log.d("DDD","HomeFragment -> onViewCreate addNote Button Click")
            viewModel.openAddNoteScreen()
        }

        homeAdapter.setLongClickListener {
            viewModel.showDialog(requireContext(), it.id, it.title)
        }

        homeAdapter.setEditClickListener { note ->
            viewModel.openEditNote(note)
        }

        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            Log.d("DDD","NOtes isEmpty -> ${it.isEmpty()}")
            if (it.isEmpty()) {
                binding.imageEmptyBox.visibility = View.VISIBLE
                homeAdapter.submitList(it)
                recyclerViewHome.adapter = homeAdapter
            } else {
                binding.imageEmptyBox.visibility = View.GONE
                homeAdapter.submitList(it)
                recyclerViewHome.adapter = homeAdapter
            }
        }

        viewModel.searchNotesLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imageEmptyBox.visibility = View.VISIBLE
            } else {
                binding.imageEmptyBox.visibility = View.GONE
            }
            homeAdapter.submitList(it)
        }

        binding.recyclerViewHome.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openEditNoteScreenLiveData.collect {
                val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(it)
                findNavController().navigate(action)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openAddNoteScreenLiveData.collect {
                findNavController().navigate(R.id.action_homeFragment_to_addNote)
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