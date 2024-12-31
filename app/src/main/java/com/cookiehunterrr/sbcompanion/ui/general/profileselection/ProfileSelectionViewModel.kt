package com.cookiehunterrr.sbcompanion.ui.general.profileselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.cookiehunterrr.sbcompanion.ProgramManager
import com.cookiehunterrr.sbcompanion.R
import com.cookiehunterrr.sbcompanion.database.entities.UserMinecraftData
import com.cookiehunterrr.sbcompanion.databinding.FragmentProfileSelectionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileSelectionViewModel : ViewModel() {
    var currentSelectedPlayerUUID: String = ""
    val selectedPlayerProfileUUIDs: List<String> = arrayListOf()

    fun fetchUserProfiles(manager: ProgramManager, userUUID: String) {}

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
        // Очищаем дропдаун с профилями
        binding.profileselectionSpinnerPlayerProfiles.adapter = null

        Glide.with(binding.root).load(R.drawable.avatar_placeholder).into(binding.profileselectionAvatar)
    }

    private fun updateSelectedUser(binding: FragmentProfileSelectionBinding,
                                   manager: ProgramManager, userData: UserMinecraftData) {
        // Убираем возможность редактировать поля для выбора пользователя
        binding.profileselectionBtnFetchUser.isEnabled = false
        binding.profileselectionEditTextUsername.isEnabled = false
        // Даем возможность сбросить выбранного пользователя
        // Скорее всего, если нажать до завершения остального кода, то нормально не сбросится
        binding.profileselectionBtnResetUser.isEnabled = true
        // Устанавливаем данные пользователя на экране
        Glide.with(binding.root).load(userData.userAvatarLink).into(binding.profileselectionAvatar)
        binding.profileselectionEditTextUsername.setText(userData.username)
        fetchUserProfiles(manager, userData.userUUID)
    }
}