package net.example.smsreader.presenter.chat_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.example.smsreader.data.ChatEntry
import net.example.smsreader.data.SmsEntry

class ChatListViewModel : ViewModel() {
    private val _messageEntries = MutableLiveData<List<SmsEntry>>()
    val messageEntries: LiveData<List<SmsEntry>>
        get() = _messageEntries

    fun loadMessages(chatEntry: ChatEntry) {
        _messageEntries.postValue(chatEntry.messages)
    }
}