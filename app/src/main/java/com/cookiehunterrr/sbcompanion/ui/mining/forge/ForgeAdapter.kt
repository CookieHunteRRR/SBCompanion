package com.cookiehunterrr.sbcompanion.ui.mining.forge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookiehunterrr.sbcompanion.MainActivity
import com.cookiehunterrr.sbcompanion.ProgramManager
import com.cookiehunterrr.sbcompanion.R

class ForgeAdapter(
    val manager: ProgramManager,
    val activity: MainActivity
) : RecyclerView.Adapter<ForgeSlotViewHolder>() {
    val forgeSlots = manager.getProfileForgeSlots()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForgeSlotViewHolder {
        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.recyclerrow_forge_slot, parent, false)
        return ForgeSlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForgeSlotViewHolder, position: Int) {
        val forgeSlot = forgeSlots.get(position)
        holder.text_itemName.setText(forgeSlot.itemID)
        holder.text_timeLeft.setText(
            manager.forgeManager.getRemainingForgeTimeAsString(forgeSlot.itemID, forgeSlot.startTime))
    }

    override fun getItemCount(): Int {
        return forgeSlots.size
    }
}