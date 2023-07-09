package com.plicraz.zynner.game.ui.cases

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.plicraz.zynner.library.library.balance
import com.plicraz.zynner.library.library.shortToast
import com.plicraz.zynner.databinding.FragmentCasesBinding
import com.plicraz.zynner.game.domain.adapters.CasesAdapter
import com.plicraz.zynner.game.domain.other.CaseAction
import com.plicraz.zynner.game.domain.other.Cases
import com.plicraz.zynner.game.domain.other.CasesOptions
import com.plicraz.zynner.game.domain.other.CasesOptions.Companion.BIG_CASES_AMOUNT
import com.plicraz.zynner.game.domain.other.CasesOptions.Companion.MEDIUM_CASES_AMOUNT
import com.plicraz.zynner.game.domain.other.CasesOptions.Companion.SMALL_CASES_AMOUNT
import com.plicraz.zynner.game.ui.other.ViewBindingFragment
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer


class FragmentCase : ViewBindingFragment<FragmentCasesBinding>(FragmentCasesBinding::inflate) {
    private lateinit var casesAdapter: CasesAdapter
    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        setBalance()
    }

    private fun initViewPager() {
        casesAdapter = CasesAdapter { item, action ->
            when (action) {
                CaseAction.OPEN -> open(item)
                CaseAction.BUY -> buy(item)
                CaseAction.INFO -> info(item)
            }
        }
        binding.viewPagerCases.setOrientation(DSVOrientation.HORIZONTAL)
        binding.viewPagerCases.adapter = casesAdapter
        binding.viewPagerCases.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.0f)
                .setMinScale(0.8f)
                .build()
        )
        binding.viewPagerCases.setItemTransitionTimeMillis(150)
        binding.viewPagerCases.setSlideOnFling(true)
        binding.viewPagerCases.setSlideOnFlingThreshold(1000)
        setItems()
    }

    private fun info(item: Cases) {
        when (item.id) {
            0 -> {
                findNavController().navigate(
                    FragmentCaseDirections.actionFragmentCaseToFragmentCaseContent(
                        CasesOptions.getCasesWithOptions(sharedPreferences)[0]
                    )
                )
            }
            1 -> {
                findNavController().navigate(
                    FragmentCaseDirections.actionFragmentCaseToFragmentCaseContent(
                        CasesOptions.getCasesWithOptions(sharedPreferences)[1]
                    )
                )
            }
            else -> {
                findNavController().navigate(
                    FragmentCaseDirections.actionFragmentCaseToFragmentCaseContent(
                        CasesOptions.getCasesWithOptions(sharedPreferences)[2]
                    )
                )
            }
        }
    }
    private fun setBalance() {
        binding.balanceTextView.text = balance(sharedPreferences).toString()
    }

    private fun buy(item: Cases) {
        val balance = balance(sharedPreferences)
        when (item.id) {
            0 -> {
                if (item.casePrice > balance) {
                    shortToast(requireContext(), "Not enough money")
                } else {
                    decreaseBalance(item.casePrice)
                    increaseCase(BIG_CASES_AMOUNT)
                    setBalance()
                }
            }
            1 -> {
                if (item.casePrice > balance) {
                    shortToast(requireContext(), "Not enough money")
                } else {
                    decreaseBalance(item.casePrice)
                    increaseCase(MEDIUM_CASES_AMOUNT)
                    setBalance()
                }
            }
            else -> {
                if (item.casePrice > balance) {
                    shortToast(requireContext(), "Not enough money")
                } else {
                    decreaseBalance(item.casePrice)
                    increaseCase(SMALL_CASES_AMOUNT)
                    setBalance()
                }
            }
        }
        setItems()
    }

    private fun setItems() {
        casesAdapter.items = CasesOptions.getCases(sharedPreferences).toMutableList()
        casesAdapter.notifyDataSetChanged()
    }

    private fun decreaseBalance(value: Int) {
        val balance = balance(sharedPreferences)
        sharedPreferences.edit().putLong("BALANCE", balance - value).apply()
    }

    private fun open(item: Cases) {
        if (item.caseAmount == 0) {
            shortToast(requireContext(), "You have no cases left")
            return
        }
        findNavController().navigate(
            FragmentCaseDirections.actionFragmentCaseToFragmentOpenCase(
                CasesOptions.getCasesWithOptions(sharedPreferences)[item.id]
            )
        )
    }

    private fun increaseCase(case: String) {
        val casesAmount = sharedPreferences.getInt(case, 0)
        sharedPreferences.edit().putInt(case, casesAmount + 1).apply()
    }
}