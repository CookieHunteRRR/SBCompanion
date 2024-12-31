package com.cookiehunterrr.sbcompanion.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cookiehunterrr.sbcompanion.database.daos.ProfileInfoDao
import com.cookiehunterrr.sbcompanion.database.daos.UserMinecraftDataDao
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo
import com.cookiehunterrr.sbcompanion.database.entities.UserMinecraftData

@Database(entities = [
    ProfileInfo::class,
    UserMinecraftData::class
    ], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun profileInfoDao() : ProfileInfoDao
    abstract fun userMinecraftDataDao() : UserMinecraftDataDao
}