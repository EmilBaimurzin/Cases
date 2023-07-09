package com.plicraz.zynner.game.domain.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.plicraz.zynner.library.library.drawable
import com.plicraz.zynner.R
import com.plicraz.zynner.databinding.ItemOpenCaseBinding
import com.plicraz.zynner.game.domain.other.OpenCaseItem
import kotlin.math.roundToInt


class OpenCasesAdapter() :
    RecyclerView.Adapter<OpenCasesViewHolder>() {
    var items = mutableListOf<OpenCaseItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenCasesViewHolder {
        return OpenCasesViewHolder(
            ItemOpenCaseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OpenCasesViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class OpenCasesViewHolder(private val binding: ItemOpenCaseBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: OpenCaseItem) {
        binding.starLayout.removeAllViews()
        repeat(item.starsAmount) {
            val view = ImageView(context)
            view.layoutParams = LinearLayout.LayoutParams(dpToPx(25), dpToPx(25))
            view.setColorFilter(context.getColor(R.color.gold))
            view.setImageResource(R.drawable.ic_case_star)
            binding.starLayout.addView(view)
        }
        binding.imgItem.setImageResource(getImage(item.id))
        binding.root.background = getBackground(item.id)
    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    private fun getImage(id: Int): Int {
        return when (id) {
            1 -> R.drawable.item_1
            2 -> R.drawable.item_2
            3 -> R.drawable.item_3
            4 -> R.drawable.item_4
            else -> R.drawable.item_5
        }
    }

    private fun getBackground(id: Int): Drawable {
        return when (id) {
            1 -> drawable(context, R.drawable.bg_item_1)
            2 -> drawable(context, R.drawable.bg_item_2)
            3 -> drawable(context, R.drawable.bg_item_3)
            4 -> drawable(context, R.drawable.bg_item_4)
            else -> drawable(context, R.drawable.bg_item_5)
        }
    }
}