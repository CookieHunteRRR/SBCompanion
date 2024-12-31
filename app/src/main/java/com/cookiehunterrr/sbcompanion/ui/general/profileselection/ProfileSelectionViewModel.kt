package com.cookiehunterrr.sbcompanion.ui.general.profileselection

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookiehunterrr.sbcompanion.ProgramManager
import kotlinx.coroutines.launch

class ProfileSelectionViewModel : ViewModel() {
    var currentSelectedPlayerUUID: String = ""
    val selectedPlayerProfileUUIDs: List<String> = arrayListOf()

    fun fetchUserProfiles(manager: ProgramManager, username: String) {
        val fetchedProfiles = manager.db.profileInfoDao().getPlayerProfiles(username)
        if (fetchedProfiles.isEmpty()) {
            //Toast.makeText(manager.appContext, "No profiles found", Toast.LENGTH_SHORT).show()
            manager.updateProfilesForUser(username)
        }
    }
}