package com.plicraz.zynner.game.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plicraz.zynner.R
import com.plicraz.zynner.databinding.ItemCaseBinding
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.game.domain.other.CaseAction
import com.plicraz.zynner.game.domain.other.Cases

class CasesAdapter(private val itemClickListener: (item: Cases, action: CaseAction) -> Unit) :
    RecyclerView.Adapter<CasesViewHolder>() {
    var items = mutableListOf<Cases>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasesViewHolder {
        return CasesViewHolder(
            ItemCaseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CasesViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemClickListener = itemClickListener
    }
}

class CasesViewHolder(private val binding: ItemCaseBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: ((item: Cases, action: CaseAction) -> Unit)? = null
    fun bind(item: Cases) {
        binding.apply {
            setCaseImg(item.id)
            caseNameTextView.text = item.caseName
            buyButton.text = "Buy (${item.casePrice})"
            openButton.text = "Open (${item.caseAmount})"

            buyButton.soundClickListener {
                itemClickListener?.invoke(item, CaseAction.BUY)
            }
            openButton.soundClickListener {
                itemClickListener?.invoke(item, CaseAction.OPEN)
            }
            insideButton.soundClickListener {
                itemClickListener?.invoke(item, CaseAction.INFO)
            }
        }
    }

    private fun setCaseImg(id: Int) {
        when (id) {
            0 -> binding.imgCase.setImageResource(R.drawable.img_case_large)
            1 -> binding.imgCase.setImageResource(R.drawable.img_case_middle)
            else -> binding.imgCase.setImageResource(R.drawable.img_case_tiny)
        }
    }
}