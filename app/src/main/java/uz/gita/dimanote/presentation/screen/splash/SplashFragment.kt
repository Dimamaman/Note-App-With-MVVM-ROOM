package uz.gita.dimanote.presentation.screen.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import uz.gita.dimanote.R
import uz.gita.dimanote.databinding.FragmentSplashBinding
import uz.gita.dimanote.util.myApply

class SplashFragment: Fragment(R.layout.fragment_splash) {
    private val binding: FragmentSplashBinding by viewBinding()
    var mCallback: OnHideToolbar? = null
    var toolbar: Toolbar? = null

    interface OnHideToolbar {
        fun onHidingToolbar(position: Int)
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        activity?.actionBar?.show()
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.actionBar?.hide()
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(1000)
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}