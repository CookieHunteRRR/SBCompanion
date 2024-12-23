package com.cookiehunterrr.sbcompanion.ui.mining.forge

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cookiehunterrr.sbcompanion.R

class ForgeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text_itemName : TextView = itemView.findViewById(R.id.forgeSlot_text_itemName)
    val text_timeLeft : TextView = itemView.findViewById(R.id.forgeSlot_text_timeLeft)
    val progressBar : ProgressBar = itemView.findViewById(R.id.forgeSlot_progressBar)
}