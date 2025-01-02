package com.cookiehunterrr.sbcompanion

import android.content.Context
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.cookiehunterrr.sbcompanion.database.Database
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo
import com.cookiehunterrr.sbcompanion.database.entities.UserMinecraftData
import com.cookiehunterrr.sbcompanion.extra.enums.ProfileType
import org.json.JSONObject
import java.io.BufferedReader

class ProgramManager(context: Context, database: Database) {
    val db = database
    val appContext = context

    var currentProfileUUID: String = ""

    fun getForgeSlotsData() {

        val key = getApiKey()
        val request = "https://api.hypixel.net/v2/resources/skyblock/profile?key=$key&profile=$currentProfileUUID"

        getApiAnswer(request) {
            val isSuccessful = it.getBoolean("success")
            if (!isSuccessful) {
                val errorCause = it.getString("cause")
                Toast.makeText(appContext, errorCause, Toast.LENGTH_SHORT).show()
            }

            val sbVersion = it.getString("version")
            Toast.makeText(appContext, sbVersion, Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchUserMinecraftData(username: String) {
        val query = "https://playerdb.co/api/player/minecraft/$username"
        getApiAnswer(query) {
            // В случае, если API не нашел указанного юзера
            if (!it.getBoolean("success")) {
                Toast.makeText(appContext, "No username $username found", Toast.LENGTH_LONG).show()
                return@getApiAnswer
            }
            // Если указанный юзер найден
            val data = it.getJSONObject("data").getJSONObject("player")
            val newUser = UserMinecraftData(
                data.getString("username"),
                data.getString("id"),
                data.getString("avatar")
            )
            db.userMinecraftDataDao().insert(newUser)
        }
    }

    fun fetchUserSkyblockProfiles(userUUID: String) {
        // Update cached data for all profiles with one api call
        val key = getApiKey()
        //val userUUID = findUserUUIDByUsername(username)
        val request = "https://api.hypixel.net/v2/skyblock/profiles?key=$key&uuid=$userUUID"

        getApiAnswer(request) {
            val profileArray = it.getJSONArray("profiles")
            for (profileIndex: Int in 0..<profileArray.length()) {
                saveProfileDataInDB(profileArray.getJSONObject(profileIndex), userUUID)
            }
        }
    }

    private fun saveProfileDataInDB(profileObject: JSONObject, userUUID: String) {
        val profileInfo = ProfileInfo(
            profileObject.getString("profile_id"),
            userUUID,
            profileObject.getString("cute_name"),
            getProfileType(profileObject)
        )
        db.profileInfoDao().insert(profileInfo)
    }

    private fun getApiAnswer(apiRequest: String, callback: (result: JSONObject) -> Unit) {
        val queue = Volley.newRequestQueue(appContext)

        val jsonObjectRequest = JsonObjectRequest(apiRequest, { response ->
            callback(response)
        }, { errorResponse ->
            Toast.makeText(appContext, errorResponse.toString(), Toast.LENGTH_LONG).show()
        })
        queue.add(jsonObjectRequest)
    }

    private fun getApiKey() : String {
        val inputStream = appContext.assets.open("key.txt")
        val reader = BufferedReader(inputStream.reader())
        val firstLine = reader.readLine()
        reader.close()
        if (firstLine != null) return firstLine
        return ""
    }

    private fun getProfileType(jsonObject: JSONObject) : String {
        if (!jsonObject.has("game_mode")) {
            return ProfileType.NORMAL.toString()
        }

        return when (jsonObject.getString("game_mode")) {
            "ironman" -> ProfileType.IRONMAN.toString()
            "bingo" -> ProfileType.BINGO.toString()
            "stranded" -> ProfileType.STRANDED.toString()
            else -> ""
        }
    }
}