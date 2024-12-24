package com.cookiehunterrr.sbcompanion.ui.general.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cookiehunterrr.sbcompanion.MainActivity
import com.cookiehunterrr.sbcompanion.R
import com.cookiehunterrr.sbcompanion.databinding.FragmentProfileBinding
import com.cookiehunterrr.sbcompanion.ui.general.profileselection.ProfileSelectionFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textViewDebug
        //(activity as MainActivity).programManager.getForgeSlotsData(textView)
        /*
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}