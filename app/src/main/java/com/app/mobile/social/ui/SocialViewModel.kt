package com.app.mobile.social.ui

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mobile.social.SingleLiveEvent
import com.app.mobile.social.data.SocialModel
import com.app.mobile.social.data.SocialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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

    val browserEvent = SingleLiveEvent<String>()
    val itemEvent = SingleLiveEvent<SocialModel.Social>()

    private var currentPage = 0
    private val LOAD_SIZE = 10

    init {
        loadSocials()
    }

    fun loadSocials() {
        _dataLoading.value = true
        viewModelScope.launch {
            delay(1000)
            socialRepository.getItems(currentPage, LOAD_SIZE).collect {
                _items.value = _items.value?.toMutableList()?.also { originList ->
                    if (it.isNotEmpty()) {
                        originList += it.toMutableList()
                        currentPage++
                    }

                }
                _dataLoading.value = false
            }
        }
    }

    fun onLikeEvent(item : SocialModel.Social){
        viewModelScope.launch {
            socialRepository.like(item.idx,!item.isLike).collect{
                item.isLike = !item.isLike
                item.like = it
                _items.value = _items.value?.toMutableList()
            }
        }

    }

    fun onADEvent(item : SocialModel.AD){
        browserEvent.value = item.url
    }

    fun onItemEvent(item : SocialModel.Social){
        itemEvent.value = item
    }

    fun onSendReply(idx: Long, reply: String) {
        if(reply.isEmpty()) return
        val name = "hsik"
        _dataLoading.value = true
        viewModelScope.launch {
            delay(1000)
            _dataLoading.value = false
            socialRepository.sendReply(idx = idx, name = name, reply = reply).collect { result ->
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