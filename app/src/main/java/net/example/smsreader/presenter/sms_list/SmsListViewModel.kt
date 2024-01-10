package net.example.smsreader.presenter.sms_list

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.example.smsreader.data.SmsEntry
import net.example.smsreader.data.ChatEntry
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SmsListViewModel: ViewModel() {

    private val _chatMessageEntries = MutableLiveData<List<ChatEntry>>()
    val chatMessageEntries: LiveData<List<ChatEntry>>
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
                result.list.add(address to SmsEntry(body, timestampToString(time.toLong()), isSent))
            } while (cursor.moveToNext())
        }

        cursor?.close() // cursor в результате всегда должен закрываться

        _chatMessageEntries.postValue(result.toSmsChatEntriesList())
    }

    private fun timestampToString(timestamp: Long) =
        formatter.format(convertMillisToDateTime(timestamp))


    private fun convertMillisToDateTime(millis: Long): LocalDateTime {
        val instant = Instant.ofEpochMilli(millis)
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    companion object {
        private const val TIME_FORMAT = "HH:mm"
        private val formatter =
            DateTimeFormatter.ofPattern(TIME_FORMAT)
    }
}

@JvmInline
value class AddressAndMessageList(
    val list: MutableList<Pair<String, SmsEntry>>
) {
    fun toSmsChatEntriesList(): List<ChatEntry> =
        list
            .groupBy { it.first }
            .map {
                val messages = it.value.map { it.second }
                ChatEntry(it.key, messages)
            }
}