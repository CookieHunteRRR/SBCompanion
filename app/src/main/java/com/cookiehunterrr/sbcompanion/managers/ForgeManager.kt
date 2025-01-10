package com.cookiehunterrr.sbcompanion.managers

import com.cookiehunterrr.sbcompanion.database.entities.ForgeSlot
import com.cookiehunterrr.sbcompanion.database.entities.ProfileInfo

class ForgeManager {
    private val forgeTimesInSeconds = mapOf(
        "PERFECT_RUBY_GEM" to 72000L,
        "PERFECT_AMBER_GEM" to 72000L,
        "PERFECT_AMETHYST_GEM" to 72000L,
        "PERFECT_JADE_GEM" to 72000L,
        "PERFECT_SAPPHIRE_GEM" to 72000L,
        "PERFECT_TOPAZ_GEM" to 72000L,
        "PERFECT_JASPER_GEM" to 72000L,
        "PERFECT_OPAL_GEM" to 72000L,
        "PERFECT_ONYX_GEM" to 72000L,
        "PERFECT_CITRINE_GEM" to 72000L,
        "PERFECT_AQUAMARINE_GEM" to 72000L,
        "PERFECT_PERIDOT_GEM" to 72000L,
        "REFINED_DIAMOND" to 28800L,
        "REFINED_MITHRIL" to 21600L,
        "REFINED_TITANIUM" to 43200L,
        "REFINED_UMBER" to 3600L,
        "REFINED_TUNGSTEN" to 3600L,
        "BEJEWELED_HANDLE" to 30L,
        "DRILL_ENGINE" to 108000L,
        "FUEL_TANK" to 36000L,
        "GEMSTONE_MIXTURE" to 14400L,
        "GLACITE_AMALGAMATION" to 14400L,
        "GOLDEN_PLATE" to 21600L,
        "MITHRIL_PLATE" to 64800L,
        "TUNGSTEN_PLATE" to 10800L,
        "UMBER_PLATE" to 10800L,
        "PERFECT_PLATE" to 1800L,
        "SKELETON_KEY" to 1800L
    )

    fun getRemainingForgeTimeAsString(itemId: String, startTime: Long) : String {
        if (!forgeTimesInSeconds.containsKey(itemId)) {
            return "UNKNOWN ITEM"
        }
        val timeLeft = getRemainingForgeTime(itemId, startTime)
        if (timeLeft < 1) {
            return "ENDED"
        }
        return convertToBeautifulTimeString(timeLeft)
    }

    fun isForgeSlotsRequireUpdate(forgeSlots: List<ForgeSlot>, profileInfo: ProfileInfo) : Boolean {
        val currentTime = java.util.Date().time
        val lastProfileUpdate = profileInfo.lastUpdate
        if (currentTime - lastProfileUpdate > 300 * 1000) {
            return true
        }
        for (slotIndex in forgeSlots.indices) {
            val forgeSlot = forgeSlots.get(slotIndex)
            if (getRemainingForgeTime(forgeSlot.itemID, forgeSlot.startTime) < 1) {
                return true
            }
        }
        return false
    }

    private fun getRemainingForgeTime(itemId: String, startTime: Long) : Long {
        val currentTime = java.util.Date().time
        val requiredTime = forgeTimesInSeconds[itemId]!! * 1000
        val timeLeft = (startTime + requiredTime) - currentTime
        return timeLeft
    }

    private fun convertToBeautifulTimeString(time: Long) : String {
        var timeInSeconds = time / 1000
        if (timeInSeconds < 60) {
            return "${timeInSeconds}s"
        }
        var minutes = timeInSeconds / 60
        timeInSeconds -= minutes * 60
        if (minutes < 60) {
            return "${minutes}m${timeInSeconds}s"
        }
        var hours = minutes / 60
        minutes -= hours * 60
        if (hours < 24) {
            return "${hours}h${minutes}m${timeInSeconds}s"
        }
        var days = hours / 24
        hours -= days * 24
        return "${days}d${hours}h${minutes}m${timeInSeconds}s"
    }
}