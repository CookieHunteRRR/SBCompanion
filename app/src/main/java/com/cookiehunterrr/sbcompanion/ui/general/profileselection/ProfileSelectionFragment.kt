package com.cookiehunterrr.sbcompanion.ui.general.profileselection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cookiehunterrr.sbcompanion.MainActivity
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
        val programManager = (activity as MainActivity).programManager

        val usernameField = binding.profileselectionEditTextUsername

        val fetchUserBtn = binding.profileselectionBtnFetchUser
        fetchUserBtn.setOnClickListener {
            profileSelectionViewModel.fetchUserMinecraftData(binding, programManager, usernameField.text.toString())
        }

        val resetUserBtn = binding.profileselectionBtnResetUser
        resetUserBtn.setOnClickListener {
            profileSelectionViewModel.resetSelectedUser(binding)
        }

        val selectProfileBtn = binding.profileselectionBtnSelectProfile
        selectProfileBtn.setOnClickListener {
            profileSelectionViewModel.updateSelectedUserSkyblockProfile(programManager)
        }

        val profileSelectionSpinner = binding.profileselectionSpinnerPlayerProfiles
        val arrayAdapter = ArrayAdapter<String>(this.requireContext(),
            android.R.layout.simple_spinner_item,
            profileSelectionViewModel.selectedPlayerProfileNames)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        profileSelectionSpinner.adapter = arrayAdapter
        profileSelectionSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                profileSelectionViewModel.onSkyblockProfileSelected(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(this.requireContext(), "AUU", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this.requireContext(), "NUH UH", Toast.LENGTH_SHORT).show()
    }*/
}