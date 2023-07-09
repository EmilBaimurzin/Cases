package com.plicraz.zynner.game.domain.other

import android.content.SharedPreferences
import java.io.Serializable

data class CasesOptions(
    val id: Int,
    val caseName: String,
    val casePrice: Int,
    val caseAmount: Int,
    val item1Chance: Float,
    val item2Chance: Float,
    val item3Chance: Float,
    val item4Chance: Float,
    val item5Chance: Float,
) : Serializable {
    companion object {
        const val BIG_CASES_AMOUNT = "BIG_CASES_AMOUNT"
        const val MEDIUM_CASES_AMOUNT = "MEDIUM_CASES_AMOUNT"
        const val SMALL_CASES_AMOUNT = "SMALL_CASES_AMOUNT"
        fun getCases(sp: SharedPreferences): List<Cases> {
            return mutableListOf(
                Cases(0, "Big Case", 1000, sp.getInt(BIG_CASES_AMOUNT, 0)),
                Cases(1, "Medium Case", 500, sp.getInt(MEDIUM_CASES_AMOUNT, 0)),
                Cases(2, "Small Case", 200, sp.getInt(SMALL_CASES_AMOUNT, 0)),
            )
        }

        fun getCasesWithOptions(sp: SharedPreferences): List<CasesOptions> {
            return listOf(
                CasesOptions(
                    0, "Big Case", 1000, sp.getInt(BIG_CASES_AMOUNT, 0),
                    item1Chance = 3f,
                    item2Chance = 8f,
                    item3Chance = 15f,
                    item4Chance = 45f,
                    item5Chance = 29f,
                ),
                CasesOptions(1, "Medium Case", 500, sp.getInt(MEDIUM_CASES_AMOUNT, 0),
                    item1Chance = 1f,
                    item2Chance = 3f,
                    item3Chance = 15f,
                    item4Chance = 35f,
                    item5Chance = 46f,
                    ),
                CasesOptions(2, "Small Case", 200, sp.getInt(SMALL_CASES_AMOUNT, 0),
                    item1Chance = 0f,
                    item2Chance = 2f,
                    item3Chance = 10f,
                    item4Chance = 28f,
                    item5Chance = 60f,
                    ),
            )
        }
    }
}