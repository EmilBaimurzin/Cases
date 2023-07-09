package com.plicraz.zynner.game.ui.cases

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plicraz.zynner.library.library.ViewBindingDialog
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.R
import com.plicraz.zynner.databinding.DialogCaseBinding
import com.plicraz.zynner.game.domain.other.OpenCaseItem

import kotlin.math.roundToInt

class DialogCase: ViewBindingDialog<DialogCaseBinding>(DialogCaseBinding::inflate) {
    private val args: DialogCaseArgs by navArgs()
    private lateinit var item: OpenCaseItem
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = args.item
        binding.closeButton.soundClickListener {
            findNavController().popBackStack()
        }
        binding.starLayout.removeAllViews()
        repeat(item.starsAmount) {
            val starView = ImageView(requireContext())
            starView.layoutParams = LinearLayout.LayoutParams(dpToPx(30), dpToPx(30))
            starView.setColorFilter(requireContext().getColor(R.color.gold))
            starView.setImageResource(R.drawable.ic_case_star)
            binding.starLayout.addView(starView)
        }
        binding.itemImg.setImageResource(getImage(item.id))
    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics: DisplayMetrics = requireContext().resources.displayMetrics
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
}