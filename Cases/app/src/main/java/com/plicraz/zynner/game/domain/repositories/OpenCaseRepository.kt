package com.plicraz.zynner.game.domain.repositories

import com.plicraz.zynner.library.library.random
import com.plicraz.zynner.game.database.DataBase
import com.plicraz.zynner.game.domain.other.CasesOptions
import com.plicraz.zynner.game.domain.other.OpenCaseItem
import com.plicraz.zynner.game.domain.other.StarChances
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OpenCaseRepository {
    fun generateList(caseOptions: CasesOptions): List<OpenCaseItem> {
        val list = mutableListOf<OpenCaseItem>()
        repeat(80) {
            list.add(OpenCaseItem(getRandomId(caseOptions), getRandomStars()))
        }
        return list
    }

    private fun getRandomStars(): Int {
        val list = mutableListOf<Int>()
        repeat(StarChances().oneStarChance) {
            list.add(1)
        }
        repeat(StarChances().twoStarChance) {
            list.add(2)
        }
        repeat(StarChances().threeStarChance) {
            list.add(3)
        }
        return list[0 random 99]
    }

    private fun getRandomId(caseOptions: CasesOptions): Int {
        val list = mutableListOf<Int>()
        repeat(caseOptions.item1Chance.toInt()) {
            list.add(1)
        }
        repeat(caseOptions.item2Chance.toInt()) {
            list.add(2)
        }
        repeat(caseOptions.item3Chance.toInt()) {
            list.add(3)
        }
        repeat(caseOptions.item4Chance.toInt()) {
            list.add(4)
        }
        repeat(caseOptions.item5Chance.toInt()) {
            list.add(5)
        }
        return list[0 random 99]
    }

    fun addToDB(openCaseItem: OpenCaseItem) {
        CoroutineScope(Dispatchers.IO).launch {
            DataBase.instance.dao().addToDB(openCaseItem.convertToDB())
        }
    }
}