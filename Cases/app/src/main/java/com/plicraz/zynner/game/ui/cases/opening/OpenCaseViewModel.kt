package com.plicraz.zynner.game.ui.cases.opening

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plicraz.zynner.game.domain.other.CasesOptions
import com.plicraz.zynner.game.domain.other.OpenCaseItem
import com.plicraz.zynner.game.domain.repositories.OpenCaseRepository

class OpenCaseViewModel(private val caseOptions: CasesOptions): ViewModel() {
    private val repository = OpenCaseRepository()
    private val _list = MutableLiveData(repository.generateList(caseOptions))
    val list: LiveData<List<OpenCaseItem>> = _list
    var currentItem = 0
    private val _isScrolling = MutableLiveData(false)
    val isScrolling: LiveData<Boolean> = _isScrolling

    fun changeScrollState(value: Boolean) {
        _isScrolling.postValue(value)
    }

    fun generateList() {
        _list.postValue(repository.generateList(caseOptions))
    }

    fun addToDB(openCaseItem: OpenCaseItem) {
        repository.addToDB(openCaseItem)
    }
}

class OpenCaseViewModelFactory(private val caseOptions: CasesOptions): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OpenCaseViewModel(caseOptions = caseOptions) as T
    }
}