package com.example.urlshortenerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortenerapp.data.repository.UrlShortenerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: UrlShortenerRepository,
    sharingStarted: SharingStarted = SharingStarted.WhileSubscribed(5_000)
) : ViewModel(),
    LinksListActions {
    private val inputValue = MutableStateFlow("")

    private val linkListStatus: MutableStateFlow<LinksListState.Status> =
        MutableStateFlow(LinksListState.Status.Done)

    private val linkHistoryFlow =
        repository
            .getHistory()
            .map { LinksListState.LinkHistory.from(it) }
            .distinctUntilChanged()
            .stateIn(viewModelScope, sharingStarted, emptyList())

    val linkListState =
        combine(
            inputValue,
            linkListStatus,
            linkHistoryFlow
        ) { input, status, history ->
            LinksListState(
                inputValue = input,
                history = history,
                status = status
            )
        }.stateIn(viewModelScope, sharingStarted, LinksListState())

    private fun validateUrl(url: String): Boolean = url.isNotBlank()

    override fun onInputValueChanged(input: String) {
        inputValue.value = input
    }

    override fun onCommit() {
        val url = inputValue.value
        if (!validateUrl(url)) {
            linkListStatus.value = LinksListState.Status.Error("Invalid Url")
            return
        }

        viewModelScope.launch {
            linkListStatus.value = LinksListState.Status.Loading
            val result = repository.doShort(url)
            val nextStatus = if (result.isSuccess) {
                inputValue.value = ""
                LinksListState.Status.Done
            } else {
                LinksListState.Status.Error(
                    "It wasn't possible to shorten your url, try again later"
                )
            }
            linkListStatus.value = nextStatus
        }
    }
}
