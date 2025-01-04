package com.cookiehunterrr.sbcompanion.ui.mining.forge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cookiehunterrr.sbcompanion.MainActivity
import com.cookiehunterrr.sbcompanion.databinding.FragmentMiningForgeBinding

class MiningForgeFragment : Fragment() {

    private var _binding: FragmentMiningForgeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val forgeViewModel =
            ViewModelProvider(this).get(MiningForgeViewModel::class.java)

        _binding = FragmentMiningForgeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mainActivity = this.requireContext() as MainActivity
        val forgeAdapter = ForgeAdapter(mainActivity.programManager, mainActivity)
        val recyclerView: RecyclerView = binding.recyclerViewForgeSlots
        recyclerView.adapter = forgeAdapter
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}