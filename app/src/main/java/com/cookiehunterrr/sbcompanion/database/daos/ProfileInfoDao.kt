package com.cookiehunterrr.sbcompanion.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo

@Dao
interface ProfileInfoDao {
    @Query("SELECT * FROM profileInfos WHERE playerUUID LIKE :playerUUID")
    fun getPlayerProfiles(playerUUID: String) : List<ProfileInfo>

    @Query("SELECT * FROM profileInfos WHERE playerUUID LIKE :playerUUID AND profileName LIKE :profileName LIMIT 1")
    fun getPlayerProfileByName(playerUUID: String, profileName: String) : ProfileInfo?

    @Insert
    fun insert(vararg users: ProfileInfo)

    @Delete
    fun delete(user: ProfileInfo)
}