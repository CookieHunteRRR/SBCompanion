package com.cookiehunterrr.sbcompanion.ui.general.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cookiehunterrr.sbcompanion.MainActivity
import com.cookiehunterrr.sbcompanion.databinding.FragmentProfileBinding

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

        val mainActivity = (activity as MainActivity)
        val manager = mainActivity.programManager
        if (!manager.isCurrentUserSet()) {
            mainActivity.moveToProfileSelection()
            return root
        }

        profileViewModel.setViewCurrentUserData(manager, binding)

        val forceUpdateBtn = binding.profileBtnForceupdate
        forceUpdateBtn.setOnClickListener {
            profileViewModel.forceUpdateProfile(manager)
        }

        val resetProfileBtn = binding.profileBtnResetprofile
        resetProfileBtn.setOnClickListener {
            profileViewModel.resetCurrentProfile(manager)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}