package net.example.smsreader.presenter.sms_list

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.example.smsreader.data.SmsChatEntry
import net.example.smsreader.data.MessageEntry

class SmsListViewModel: ViewModel() {

    private val _chatMessageEntries = MutableLiveData<List<SmsChatEntry>>()
    val chatMessageEntries: LiveData<List<SmsChatEntry>>
        get() = _chatMessageEntries


    fun loadSmsMessages(contentResolver: ContentResolver) {
        val cursor = contentResolver.query( // Делаем запрос в системный ContentProvider
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val result = AddressAndMessageList(mutableListOf())

        if (cursor?.moveToFirst() == true) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                val isSent = type == Telephony.Sms.MESSAGE_TYPE_SENT
                result.list.add(address to MessageEntry(body, timestampToString(time.toLong()), isSent))
            } while (cursor.moveToNext())
        }

        cursor?.close() // cursor в результате всегда должен закрываться

        _chatMessageEntries.postValue(result.toSmsChatEntriesList())
    }
}

@JvmInline
value class AddressAndMessageList(
    val list: MutableList<Pair<String, MessageEntry>>
) {
    fun toSmsChatEntriesList(): List<SmsChatEntry> =
        list
            .groupBy { it.first }
            .map {
                val messages = it.value.map { it.second }
                SmsChatEntry(it.key, messages)
            }
}