package com.cookiehunterrr.sbcompanion.ui.mining.forge

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cookiehunterrr.sbcompanion.R

class MiningForgeFragment : Fragment() {

    companion object {
        fun newInstance() = MiningForgeFragment()
    }

    private val viewModel: MiningForgeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_mining_forge, container, false)
    }
}