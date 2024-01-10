package net.example.smsreader.presenter.chat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.example.smsreader.data.SmsEntry
import net.example.smsreader.databinding.SmsEntryItemBinding

class ChatEntriesAdapter(
) : RecyclerView.Adapter<ChatEntriesAdapter.ChatEntryViewHolder>() {

    private val list = mutableListOf<SmsEntry>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatEntryViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = SmsEntryItemBinding.inflate(layoutInflater, parent, false)
        return ChatEntryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<SmsEntry>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    inner class ChatEntryViewHolder(
        private val binding: SmsEntryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: SmsEntry) = with(binding) {
            body.text = entry.message

            time.text = entry.time
        }
    }
}