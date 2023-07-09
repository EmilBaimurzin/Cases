package com.plicraz.zynner.game.domain.adapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.plicraz.zynner.R
import com.plicraz.zynner.databinding.ItemInventoryBinding
import com.plicraz.zynner.game.domain.other.ItemPrice
import com.plicraz.zynner.game.domain.other.OpenCaseItem
import kotlin.math.roundToInt


class InventoryAdapter(private val itemClickListener: (item: OpenCaseItem) -> Unit) :
    RecyclerView.Adapter<InventoryViewHolder>() {
    var items = mutableListOf<OpenCaseItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        return InventoryViewHolder(
            ItemInventoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemClickListener = itemClickListener
    }
}

class InventoryViewHolder(private val binding: ItemInventoryBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((item: OpenCaseItem) -> Unit)? = null
    fun bind(item: OpenCaseItem) {
        binding.starLayout.removeAllViews()
        repeat(item.starsAmount) {
            val view = ImageView(context)
            view.layoutParams = LinearLayout.LayoutParams(dpToPx(25), dpToPx(25))
            view.setColorFilter(context.getColor(R.color.gold))
            view.setImageResource(R.drawable.ic_case_star)
            binding.starLayout.addView(view)
        }
        binding.imgItem.setImageResource(ItemPrice().getImage(item.id))
        binding.sellButton.text = "Sell: " + ItemPrice().getPrice(item).toInt().toString()
        binding.sellButton.setOnClickListener {
            itemClickListener?.invoke(item)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }
}