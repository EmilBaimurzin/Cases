package com.plicraz.zynner.game.ui.cases

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.databinding.FragmentCaseContentBinding
import com.plicraz.zynner.game.domain.other.CasesOptions
import com.plicraz.zynner.game.ui.other.ViewBindingFragment

class FragmentCaseContent :
    ViewBindingFragment<FragmentCaseContentBinding>(FragmentCaseContentBinding::inflate) {
    private val args: FragmentCaseContentArgs by navArgs()
    private lateinit var caseOptions: CasesOptions
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        caseOptions = args.caseOptions
        binding.apply {
            caseNameTextView.text = caseOptions.caseName
            priceTextView.text = "Price: ${caseOptions.casePrice}"
            amountTextView.text = "Amount: ${caseOptions.caseAmount}"
            drop1TextView.text = caseOptions.item1Chance.toString() + "%"
            drop2TextView.text = caseOptions.item2Chance.toString() + "%"
            drop3TextView.text = caseOptions.item3Chance.toString() + "%"
            drop4TextView.text = caseOptions.item4Chance.toString() + "%"
            drop5TextView.text = caseOptions.item5Chance.toString() + "%"
        }
        binding.buttonBack.soundClickListener {
            findNavController().popBackStack()
        }
    }
}