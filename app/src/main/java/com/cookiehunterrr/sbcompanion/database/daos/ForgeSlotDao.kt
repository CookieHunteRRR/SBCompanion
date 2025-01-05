package com.cookiehunterrr.sbcompanion.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cookiehunterrr.sbcompanion.database.entities.ForgeSlot

@Dao
interface ForgeSlotDao {
    @Query("SELECT * FROM forgeSlots WHERE profileUUID LIKE :profileUUID")
    fun getProfileForgeSlots(profileUUID: String) : List<ForgeSlot>

    @Query("DELETE FROM forgeSlots WHERE profileUUID LIKE :profileUUID AND forgeSlotNumber LIKE :slotIndex")
    fun deleteForgeSlotOfProfile(profileUUID: String, slotIndex: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg slots: ForgeSlot)
}