package com.plicraz.zynner.game.ui.Inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plicraz.zynner.game.domain.other.OpenCaseItem
import com.plicraz.zynner.game.domain.repositories.InventoryRepository
import kotlinx.coroutines.launch

class InventoryViewModel : ViewModel() {
    private val repository = InventoryRepository()
    private val _list = MutableLiveData<List<OpenCaseItem>>()
    val list: LiveData<List<OpenCaseItem>> = _list

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {
            _list.postValue(repository.getItems())
        }
    }

    fun removeItem(itemId: Int) {
        viewModelScope.launch {
            repository.removeItem(itemId)
        }
    }
}