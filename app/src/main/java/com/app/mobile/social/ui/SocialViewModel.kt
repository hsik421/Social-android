package com.app.mobile.social.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mobile.social.data.SocialModel
import com.app.mobile.social.data.SocialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val socialRepository: SocialRepository
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _items = MutableLiveData<List<SocialModel.Resource>>().apply { value = emptyList() }
    val items: LiveData<List<SocialModel.Resource>> = _items

    var currentPage = 0

    init {
        loadSocials()
    }

    fun loadSocials() {
        _dataLoading.value = true
        viewModelScope.launch {
            delay(1000)
            socialRepository.getItems(currentPage, 6).collect {
                val tempList = _items.value?.toMutableList() ?: mutableListOf()
                tempList += it.toMutableList()
                _items.value = tempList
                currentPage++
                _dataLoading.value = false
            }
        }
    }

    fun sendReply(idx: Int, reply: String) {
        val name = "hsik"
        _dataLoading.value = true
        viewModelScope.launch {
            delay(1000)
            _dataLoading.value = false
            socialRepository.sendReply(idx = idx, name = "hsik", reply = reply).collect { result ->
                if (result) {
                    _items.value = _items.value?.toMutableList()?.also {
                        val social = it.find { find -> find.idx == idx } as? SocialModel.Social
                        social?.replies?.add(SocialModel.Reply(idx = idx, name = name, reply))
                    }
                }

            }
        }
    }

}