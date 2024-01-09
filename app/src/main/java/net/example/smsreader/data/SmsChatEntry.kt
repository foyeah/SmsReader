package net.example.smsreader.data

import java.io.Serializable

data class SmsChatEntry(
    val address: String,
    val messages: List<MessageEntry>
): Serializable