package com.cookiehunterrr.sbcompanion.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cookiehunterrr.sbcompanion.database.daos.ProfileInfoDao
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo

@Database(entities = [
    ProfileInfo::class
    ], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun profileInfoDao() : ProfileInfoDao
}