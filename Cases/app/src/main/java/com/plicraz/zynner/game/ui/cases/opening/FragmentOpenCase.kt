package com.plicraz.zynner.game.ui.cases.opening

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plicraz.zynner.library.library.getVolumeState
import com.plicraz.zynner.library.library.safe
import com.plicraz.zynner.library.library.shortToast
import com.plicraz.zynner.library.library.soundClickListener
import com.plicraz.zynner.R
import com.plicraz.zynner.game.common.CaseLayoutManager
import com.plicraz.zynner.databinding.FragmentOpenCaseBinding
import com.plicraz.zynner.game.domain.adapters.OpenCasesAdapter
import com.plicraz.zynner.game.domain.other.CasesOptions
import com.plicraz.zynner.game.ui.other.ViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FragmentOpenCase :
    ViewBindingFragment<FragmentOpenCaseBinding>(FragmentOpenCaseBinding::inflate) {
    private val args: FragmentOpenCaseArgs by navArgs()
    private lateinit var caseOptions: CasesOptions
    private val viewModel: OpenCaseViewModel by viewModels {
        OpenCaseViewModelFactory(args.caseOptions)
    }
    private val soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
    private val soundMap = HashMap<Int, Int>()
    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    }
    private lateinit var openCaseAdapter: OpenCasesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundMap.put(1, soundPool.load(requireContext(), R.raw.sound_opening, 1))
        caseOptions = args.caseOptions
        initAdapter()
        addScrollListener()
        setAmountText()
        binding.openCaseButton.soundClickListener {
            if (getCasesAmount() != 0) {
                removeCase()
                setAmountText()
                viewModel.changeScrollState(true)
                scroll()
            } else {
                shortToast(requireContext(), "No cases left")
            }
        }
        binding.apply {
            drop1TextView.text = caseOptions.item1Chance.toInt().toString() + "%"
            drop2TextView.text = caseOptions.item2Chance.toInt().toString() + "%"
            drop3TextView.text = caseOptions.item3Chance.toInt().toString() + "%"
            drop4TextView.text = caseOptions.item4Chance.toInt().toString() + "%"
            drop5TextView.text = caseOptions.item5Chance.toInt().toString() + "%"
        }
        viewModel.list.observe(viewLifecycleOwner) {
            openCaseAdapter.items = it.toMutableList()
            openCaseAdapter.notifyDataSetChanged()
            binding.caseRV.scrollToPosition(0)
        }
        viewModel.isScrolling.observe(viewLifecycleOwner) {
            binding.openCaseButton.isEnabled = !it
        }
    }

    private fun initAdapter() {
        openCaseAdapter = OpenCasesAdapter()
        with(binding.caseRV) {
            adapter = openCaseAdapter
            layoutManager = CaseLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            setHasFixedSize(true)
        }
    }

    private fun setAmountText() {
        binding.amountTextView.text = "Cases left: " + getCasesAmount().toString()
    }

    private fun addScrollListener() {
        binding.caseRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val centerView = recyclerView.findChildViewUnder(
                    (recyclerView.width / 2).toFloat(),
                    (recyclerView.height / 2).toFloat()
                )
                if (centerView != null) {
                    val position = recyclerView.getChildAdapterPosition(centerView)
                    if (getVolumeState(sharedPreferences)
                        && position != viewModel.currentItem
                        && viewModel.isScrolling.value!!
                    ) {
                        val soundId = soundMap[1]!!
                        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                    }
                    viewModel.currentItem = position
                    if (position == 53 && viewModel.isScrolling.value!!) {
                        viewModel.changeScrollState(false)
                        lifecycleScope.launch {
                            delay(1500)
                            end()
                        }
                    }
                }
            }
        })
    }

    private fun scroll() {
        val lm = binding.caseRV.layoutManager as CaseLayoutManager
        lm.smoothScrollToPosition(binding.caseRV, RecyclerView.State(), 55)
    }

    private fun end() {
        viewModel.addToDB(viewModel.list.value!![viewModel.currentItem])
        safe {
            findNavController().navigate(
                FragmentOpenCaseDirections.actionFragmentOpenCaseToDialogCase(
                    viewModel.list.value!![viewModel.currentItem]
                )
            )
        }
        viewModel.currentItem = 0
        viewModel.generateList()
    }

    private fun getCasesAmount(): Int {
        return when (caseOptions.id) {
            0 -> sharedPreferences.getInt(CasesOptions.BIG_CASES_AMOUNT, 0)
            1 -> sharedPreferences.getInt(CasesOptions.MEDIUM_CASES_AMOUNT, 0)
            else -> sharedPreferences.getInt(CasesOptions.SMALL_CASES_AMOUNT, 0)
        }
    }

    private fun removeCase() {
        when (caseOptions.id) {
            0 -> sharedPreferences.edit()
                .putInt(CasesOptions.BIG_CASES_AMOUNT, getCasesAmount() - 1).apply()
            1 -> sharedPreferences.edit()
                .putInt(CasesOptions.MEDIUM_CASES_AMOUNT, getCasesAmount() - 1).apply()
            else -> sharedPreferences.edit()
                .putInt(CasesOptions.SMALL_CASES_AMOUNT, getCasesAmount() - 1).apply()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.caseRV.stopScroll()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isScrolling.value!!) {
            binding.caseRV.scrollToPosition(viewModel.currentItem)
            scroll()
        }
    }
}