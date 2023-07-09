package com.plicraz.zynner.game.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.R
import com.plicraz.zynner.databinding.FragmentMenuBinding
import com.plicraz.zynner.game.ui.other.ViewBindingFragment

class FragmentMenu : ViewBindingFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            casesButton.soundClickListener {
                findNavController().navigate(R.id.action_fragmentMenu_to_fragmentCase)
            }
            infoButton.soundClickListener {
                findNavController().navigate(R.id.action_fragmentMenu_to_fragmentRules)
            }
            settingsButton.soundClickListener {
                findNavController().navigate(R.id.action_fragmentMenu_to_fragmentSettings)
            }
            inventoryButton.soundClickListener {
                findNavController().navigate(R.id.action_fragmentMenu_to_fragmentInventory)
            }
        }
    }
}