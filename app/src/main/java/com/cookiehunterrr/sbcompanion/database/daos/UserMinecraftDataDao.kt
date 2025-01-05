package com.cookiehunterrr.sbcompanion.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cookiehunterrr.sbcompanion.database.entities.UserMinecraftData

@Dao
interface UserMinecraftDataDao {
    @Query("SELECT * FROM userMinecraftData WHERE username LIKE :username LIMIT 1")
    fun getUserMinecraftData(username: String) : UserMinecraftData?

    @Query("SELECT * FROM userMinecraftData WHERE userUUID LIKE :userUUID LIMIT 1")
    fun getUserMinecraftDataByUUID(userUUID: String) : UserMinecraftData?

    @Insert
    fun insert(vararg users: UserMinecraftData)

    @Delete
    fun delete(user: UserMinecraftData)
}