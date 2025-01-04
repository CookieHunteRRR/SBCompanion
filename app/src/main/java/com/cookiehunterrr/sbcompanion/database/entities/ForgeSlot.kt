package com.cookiehunterrr.sbcompanion.database.entities

import androidx.room.Entity

@Entity(tableName = "forgeSlots", primaryKeys = ["profileUUID", "forgeSlotNumber"])
data class ForgeSlot(
    val profileUUID: String,
    val forgeSlotNumber: Int,
    val itemID: String,
    val startTime: Long
)
