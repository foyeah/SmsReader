package net.example.smsreader.presenter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import net.example.smsreader.R
import net.example.smsreader.data.SmsChatEntry
import net.example.smsreader.databinding.SmsChatEntryItemBinding

class SmsEntriesAdapter : RecyclerView.Adapter<SmsEntriesAdapter.SmsEntryViewHolder>() {

    private val list = mutableListOf<SmsChatEntry>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsEntryViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = SmsChatEntryItemBinding.inflate(layoutInflater, parent, false)
        return SmsEntryViewHolder(binding)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: SmsEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<SmsChatEntry>) {
        this.list.addAll(list)
    }

    inner class SmsEntryViewHolder(
        private val binding: SmsChatEntryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: SmsChatEntry) = with(binding) {
            val context = binding.root.context

            smsAvatar.background = getRandomBackground(context)
            smsAvatar.text = entry.address.first().toString()

            smsSender.text = entry.address
            messageAgenda.text = entry.messages.first()
        }

        private fun getRandomBackground(context: Context): Drawable? {
            val background =
                ContextCompat.getDrawable(context, R.drawable.sms_chat_entry_avatar)
            val color = context.resources.getIntArray(R.array.rainbow).random()
            background?.let { DrawableCompat.setTint(it, color) }
            return background
        }
    }
}