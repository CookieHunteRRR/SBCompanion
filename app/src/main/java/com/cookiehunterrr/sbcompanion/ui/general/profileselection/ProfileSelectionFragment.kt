package com.cookiehunterrr.sbcompanion.ui.general.profileselection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cookiehunterrr.sbcompanion.databinding.FragmentProfileSelectionBinding

class ProfileSelectionFragment : Fragment() {
    private var _binding: FragmentProfileSelectionBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileSelectionViewModel =
            ViewModelProvider(this).get(ProfileSelectionViewModel::class.java)

        _binding = FragmentProfileSelectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}