package com.cookiehunterrr.sbcompanion

import android.content.Context
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.cookiehunterrr.sbcompanion.database.Database
import com.cookiehunterrr.sbcompanion.database.entities.ForgeSlot
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo
import com.cookiehunterrr.sbcompanion.database.entities.UserMinecraftData
import com.cookiehunterrr.sbcompanion.extra.data.ForgeTimes
import com.cookiehunterrr.sbcompanion.extra.enums.ProfileType
import com.cookiehunterrr.sbcompanion.managers.ForgeManager
import org.json.JSONObject
import java.io.BufferedReader

class ProgramManager(context: Context, database: Database) {
    val db = database
    val appContext = context

    val forgeManager = ForgeManager()

    private var apiKey = ""
    private var currentProfileUUID: String = ""

    fun setCurrentUserData(profileUUID: String) {
        currentProfileUUID = profileUUID
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

    fun getProfileForgeSlots() : List<ForgeSlot> {
        val a = db.forgeSlotDao().getProfileForgeSlots(currentProfileUUID)
        // TODO: Добавить фетчинг актуальных данных если прошло 5 минут с последнего апдейта или какой-то фордж слот закончился готовиться
        return a
    }


    fun onActivityCreated() {
        if (currentProfileUUID == "") {
            (appContext as MainActivity).moveToProfileSelection()
        }
    }

    private fun updateForgeSlotsDataFromProfileJSON(profileJsonObject: JSONObject, userUUID: String) {
        val correctUserUUID = getAppropriateUserUUID(userUUID)
        if (!profileJsonObject.getJSONObject("members").has(correctUserUUID)) return

        val forgeData = profileJsonObject.getJSONObject("members").
        getJSONObject(correctUserUUID).getJSONObject("forge").
        getJSONObject("forge_processes").getJSONObject("forge_1")

        for (slotIndex in 1..7) {
            if (forgeData.has("$slotIndex")) {
                val forgeSlotJsonObject = forgeData.getJSONObject("$slotIndex")

                val forgeSlot = ForgeSlot(
                    profileJsonObject.getString("profile_id"),
                    slotIndex,
                    forgeSlotJsonObject.getString("id"),
                    forgeSlotJsonObject.getLong("startTime")
                )
                db.forgeSlotDao().insert(forgeSlot)
            }
        }
    }

    private fun saveProfileDataInDB(profileJsonObject: JSONObject, userUUID: String) {
        val profileInfo = ProfileInfo(
            profileJsonObject.getString("profile_id"),
            userUUID,
            profileJsonObject.getString("cute_name"),
            getProfileType(profileJsonObject),
            java.util.Date().time
        )
        db.profileInfoDao().insert(profileInfo)

        updateForgeSlotsDataFromProfileJSON(profileJsonObject, userUUID)
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
        if (apiKey != "") return apiKey

        val inputStream = appContext.assets.open("key.txt")
        val reader = BufferedReader(inputStream.reader())
        apiKey = reader.readLine() ?: ""
        reader.close()
        return apiKey
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

    private fun getAppropriateUserUUID(originalUUID: String) : String {
        return originalUUID.replace("-", "")
    }
}