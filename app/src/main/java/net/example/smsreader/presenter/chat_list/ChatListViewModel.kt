package net.example.smsreader.presenter.chat_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.example.smsreader.data.SmsEntry
import net.example.smsreader.data.ChatEntry

class ChatListViewModel : ViewModel() {
    private val _messageList = MutableLiveData<List<SmsEntry>>()
    val messageEntries: LiveData<List<SmsEntry>>
        get() = _messageList

    fun loadSmsMessages(chatEntryItem : ChatEntry) {
        _messageList.postValue(chatEntryItem.messages)
    }
}