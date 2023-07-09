package com.plicraz.zynner.game.ui.info

import android.os.Bundle
import android.view.View
import com.plicraz.zynner.databinding.FragmentRulesBinding
import com.plicraz.zynner.game.ui.other.ViewBindingFragment

class FragmentRules : ViewBindingFragment<FragmentRulesBinding>(FragmentRulesBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rulesVP.adapter =
            RulesAdapter(childFragmentManager, lifecycle, arrayListOf(Page1(), Page2()))
    }
}