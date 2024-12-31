package com.cookiehunterrr.sbcompanion.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userMinecraftData")
data class UserMinecraftData(
    @PrimaryKey val username: String,
    @ColumnInfo(name="userUUID") val userUUID: String,
    @ColumnInfo(name="userAvatar") val userAvatarLink: String
)