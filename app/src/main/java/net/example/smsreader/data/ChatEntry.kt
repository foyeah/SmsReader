package net.example.smsreader.data

import java.io.Serializable

data class ChatEntry(
    val address: String,
    val messages: List<SmsEntry>
): Serializable

