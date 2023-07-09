package com.plicraz.zynner.game.ui.settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.plicraz.zynner.library.library.getVolumeState
import com.plicraz.zynner.library.library.shortToast
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.databinding.FragmentSettingsBinding
import com.plicraz.zynner.game.ui.other.ViewBindingFragment

class FragmentSettings :
    ViewBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage(getVolumeState(sharedPreferences))
        binding.apply {
            buttonBack.soundClickListener { findNavController().popBackStack() }
            buttonExitGame.soundClickListener { requireActivity().finish() }
            buttonReset.soundClickListener {
                shortToast(requireContext(), "Balance has been reset")
                sharedPreferences.edit().putLong("BALANCE", 5000).apply()
            }
            buttonVolume.soundClickListener {
                val soundValue = getVolumeState(sharedPreferences)
                sharedPreferences.edit().putBoolean("SOUND", !soundValue).apply()
                setImage(!soundValue)
            }
        }
    }

    private fun setImage(value: Boolean) {
        if (value) {
            binding.buttonVolume.text = "Volume: On"
        } else {
            binding.buttonVolume.text = "Volume: Off"
        }
    }
}