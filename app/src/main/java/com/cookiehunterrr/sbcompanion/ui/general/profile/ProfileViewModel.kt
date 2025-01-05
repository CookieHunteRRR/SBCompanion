package com.cookiehunterrr.sbcompanion.ui.general.profile

import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.cookiehunterrr.sbcompanion.ProgramManager
import com.cookiehunterrr.sbcompanion.databinding.FragmentProfileBinding

class ProfileViewModel : ViewModel() {
    fun setViewCurrentUserData(manager: ProgramManager, binding: FragmentProfileBinding) {
        val userPair = manager.getCurrentUserDataFromDB()
        if (userPair == null) return

        val avatar = binding.profileAvatar
        val nicknameText = binding.profileTextNickname
        val profileText = binding.profileTextProfilename

        //Glide.with(binding.root).load(userPair.first.userAvatarLink).into(avatar)
        nicknameText.setText(userPair.first.username)
        val text = "${userPair.second.profileName} (${userPair.second.profileType})"
        profileText.setText(text)
    }

    fun resetCurrentProfile(manager: ProgramManager) {
        manager.setCurrentUserData("", "")
        manager.onActivityCreated()
    }

    fun forceUpdateProfile(manager: ProgramManager) {
        manager.fetchUserSkyblockProfiles()
    }
}