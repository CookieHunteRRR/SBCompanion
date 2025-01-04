package com.cookiehunterrr.sbcompanion.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cookiehunterrr.sbcompanion.database.entities.ForgeSlot

@Dao
interface ForgeSlotDao {
    @Query("SELECT * FROM forgeSlots WHERE profileUUID LIKE :profileUUID")
    fun getProfileForgeSlots(profileUUID: String) : List<ForgeSlot>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg slots: ForgeSlot)

    @Delete
    fun delete(forgeSlot: ForgeSlot)
}