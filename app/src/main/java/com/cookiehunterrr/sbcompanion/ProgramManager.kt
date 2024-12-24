package com.cookiehunterrr.sbcompanion

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.BufferedReader


class ProgramManager(context: Context) {
    val appContext = context
    var currentUserUUID: String = ""
    lateinit var debugTextView: TextView

    fun getForgeSlotsData(textView: TextView) {
        debugTextView = textView

        val key = getApiKey()
        val request = "https://api.hypixel.net/v2/resources/skyblock/profiles?key=$key&uuid=$currentUserUUID"

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

    private fun getProfiles() {
        // Update cached data for all profiles with one api call
        val key = getApiKey()
        val request = "https://api.hypixel.net/v2/resources/skyblock/profiles?key=$key&uuid=$currentUserUUID"

        getApiAnswer(request) {
            val profileArray = it.getJSONArray("profiles")
            for (profileIndex: Int in 0..profileArray.length()) {

            }
        }
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
}