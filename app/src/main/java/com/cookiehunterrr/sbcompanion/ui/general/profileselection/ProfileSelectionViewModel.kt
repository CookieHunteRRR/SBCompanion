package com.cookiehunterrr.sbcompanion.ui.general.profileselection

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.cookiehunterrr.sbcompanion.ProgramManager
import com.cookiehunterrr.sbcompanion.R
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo
import com.cookiehunterrr.sbcompanion.database.entities.UserMinecraftData
import com.cookiehunterrr.sbcompanion.databinding.FragmentProfileSelectionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileSelectionViewModel : ViewModel() {
    val selectedPlayerProfileNames : MutableList<String> = mutableListOf()
    private var currentlySelectedUserUUID: String = ""
    private var currentlySelectedProfileName: String = ""

    fun fetchUserProfiles(binding: FragmentProfileSelectionBinding, manager: ProgramManager, userUUID: String) {
        // Ищем профили игрока в локальной бд
        val localProfileData = manager.db.profileInfoDao().getPlayerProfiles(userUUID)
        if (localProfileData.isEmpty()) {
            // Получаем профили игрока через API хайпикселя
            manager.fetchUserSkyblockProfiles(userUUID)
            viewModelScope.launch {
                delay(500)
                val localProfileDataAgain = manager.db.profileInfoDao().getPlayerProfiles(userUUID)
                setSkyblockProfilesForUser(binding, localProfileDataAgain)
            }
            return
        }
        setSkyblockProfilesForUser(binding, localProfileData)
    }

    fun fetchUserMinecraftData(binding: FragmentProfileSelectionBinding,
                               manager: ProgramManager, username: String) {
        // Ищем введенного юзера в локальной бд
        val localUserData = manager.db.userMinecraftDataDao().getUserMinecraftData(username)
        // Если в локальной бд юзера не нашли
        if (localUserData == null) {
            //Toast.makeText(manager.appContext, "No profiles found", Toast.LENGTH_SHORT).show()
            // Пытаемся найти юзера через API
            manager.fetchUserMinecraftData(username)
            // Ждем полсекунды, затем повторяем попытку обращения в локальную бд
            viewModelScope.launch {
                delay(500)
                val localUserDataAgain = manager.db.userMinecraftDataDao().getUserMinecraftData(username)
                if (localUserDataAgain == null) return@launch
                updateSelectedUser(binding, manager, localUserDataAgain)
            }
            return
        }
        // Если в локальной бд юзера все же нашли
        updateSelectedUser(binding, manager, localUserData)
    }

    fun resetSelectedUser(binding: FragmentProfileSelectionBinding) {
        // Возвращаем возможность ввести никнейм
        binding.profileselectionBtnFetchUser.isEnabled = true
        binding.profileselectionEditTextUsername.isEnabled = true
        // Убираем возможность сбросить выбранного пользователя
        binding.profileselectionBtnResetUser.isEnabled = false
        binding.profileselectionBtnSelectProfile.isEnabled = false
        // Очищаем дропдаун с профилями
        selectedPlayerProfileNames.clear()
        currentlySelectedUserUUID = ""
        currentlySelectedProfileName = ""

        Glide.with(binding.root).load(R.drawable.avatar_placeholder).into(binding.profileselectionAvatar)
    }

    fun onSkyblockProfileSelected(indexInProfileList: Int) {
        if (indexInProfileList < 0) return
        currentlySelectedProfileName = selectedPlayerProfileNames[indexInProfileList]
    }

    fun updateSelectedUserSkyblockProfile(manager: ProgramManager) {
        val selectedProfile = manager.db.profileInfoDao().getPlayerProfileByName(
            currentlySelectedUserUUID, currentlySelectedProfileName)
        if (selectedProfile != null) {
            manager.setCurrentUserData(selectedProfile.profileUUID)
            Toast.makeText(manager.appContext,
                "Selected ${selectedProfile.profileName} profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSelectedUser(binding: FragmentProfileSelectionBinding,
                                   manager: ProgramManager, userData: UserMinecraftData) {
        // Убираем возможность редактировать поля для выбора пользователя
        binding.profileselectionBtnFetchUser.isEnabled = false
        binding.profileselectionEditTextUsername.isEnabled = false
        // Даем возможность сбросить выбранного пользователя
        // Скорее всего, если нажать до завершения остального кода, то нормально не сбросится
        binding.profileselectionBtnResetUser.isEnabled = true
        binding.profileselectionBtnSelectProfile.isEnabled = true
        // Устанавливаем данные пользователя на экране
        currentlySelectedUserUUID = userData.userUUID
        Glide.with(binding.root).load(userData.userAvatarLink).into(binding.profileselectionAvatar)
        binding.profileselectionEditTextUsername.setText(userData.username)
        fetchUserProfiles(binding, manager, userData.userUUID)

    }

    private fun setSkyblockProfilesForUser(binding: FragmentProfileSelectionBinding, profileInfos: List<ProfileInfo>) {
        selectedPlayerProfileNames.clear()
        for (profileInfo in profileInfos) {
            selectedPlayerProfileNames.add(profileInfo.profileName)
        }
        (binding.profileselectionSpinnerPlayerProfiles.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }
}