package com.plicraz.zynner.game.ui.Inventory

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.plicraz.zynner.databinding.FragmentInventoryBinding
import com.google.android.material.snackbar.Snackbar
import com.plicraz.zynner.library.library.balance
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.game.domain.adapters.InventoryAdapter
import com.plicraz.zynner.game.domain.other.ItemPrice
import com.plicraz.zynner.game.ui.other.ViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentInventory :
    ViewBindingFragment<FragmentInventoryBinding>(FragmentInventoryBinding::inflate) {
    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    }
    private var snackBar: Snackbar? = null
    private val viewModel: InventoryViewModel by viewModels()
    private lateinit var inventoryAdapter: InventoryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setBalance()
        viewModel.list.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.isEmptyTextView.visibility = View.VISIBLE
                binding.isEmptyTextView.alpha = 0.5f
            } else {
                binding.isEmptyTextView.visibility = View.GONE
            }
            inventoryAdapter.items = it.toMutableList()
            inventoryAdapter.notifyDataSetChanged()
        }
        binding.buttonBack.soundClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        inventoryAdapter = InventoryAdapter { item ->
            snackBar?.dismiss()
            val price = ItemPrice().getPrice(item).toInt()
            snackBar = Snackbar.make(
                binding.root,
                "Are you sure you want to sell item for ${price}?",
                Snackbar.LENGTH_LONG
            )
            snackBar?.setAction("Sell") {
                sell(price, item.primaryId)
            }
            snackBar?.view?.setOnClickListener { snackBar?.dismiss() }
            snackBar?.show()
        }
        with(binding.inventoryRV) {
            adapter = inventoryAdapter
            val layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false).apply {

                }
            this.layoutManager = layoutManager
            setHasFixedSize(true)
        }
    }

    private fun setBalance() {
        binding.balanceTextView.text = balance(sharedPreferences).toString()
    }

    private fun sell(price: Int, itemId: Int) {
        viewModel.removeItem(itemId)
        sharedPreferences.edit().putLong("BALANCE", balance(sharedPreferences) + price).apply()
        lifecycleScope.launch {
            delay(50)
            viewModel.getItems()
            setBalance()
        }
    }
}