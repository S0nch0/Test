package com.example.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CoinViewModel (private val repository: Repository):ViewModel(){
    private val _coinState = MutableLiveData<CoinState>(CoinState.NoData)
    val coinState: LiveData<CoinState> = _coinState

    fun getRate(){
        _coinState.postValue(CoinState.Processing)
        viewModelScope.launch {
            val response = repository.getCoinRate()
            if (response.data?.rateUsd != null) {
                _coinState.postValue(CoinState.UpdateData(response.data.rateUsd))
            } else {
                _coinState.postValue(CoinState.Error)
            }
        }
    }

    sealed class CoinState {
        object Processing: CoinState()
        object NoData: CoinState()
        object Error: CoinState()
        class UpdateData(val rate: String): CoinState()
    }
}