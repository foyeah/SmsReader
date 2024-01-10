package net.example.smsreader.presenter.sms_list

import net.example.smsreader.data.ChatEntry

interface SmsListClickListener {
    fun onSmsListItemClick(entry: ChatEntry)
}