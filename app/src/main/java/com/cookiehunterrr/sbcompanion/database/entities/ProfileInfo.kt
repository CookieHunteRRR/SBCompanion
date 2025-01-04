package com.cookiehunterrr.sbcompanion.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profileInfos")
data class ProfileInfo(
    @PrimaryKey val profileUUID: String,
    @ColumnInfo(name="playerUUID") val playerUUID: String,
    @ColumnInfo(name = "profileName") val profileName: String,
    @ColumnInfo(name="profileType") val profileType: String,
    @ColumnInfo(name = "lastUpdate") val lastUpdate: Long
)
