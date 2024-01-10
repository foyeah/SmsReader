package net.example.smsreader.presenter.sms_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.example.smsreader.data.ChatEntry
import net.example.smsreader.databinding.ChatEntryItemBinding

class SmsEntriesAdapter(
    private val onItemClick: (ChatEntry) -> Unit,
) : RecyclerView.Adapter<SmsEntriesAdapter.SmsEntryViewHolder>() {

    private val list = mutableListOf<ChatEntry>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsEntryViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ChatEntryItemBinding.inflate(layoutInflater, parent, false)
        return SmsEntryViewHolder(binding, onItemClick)
    }

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: SmsEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<ChatEntry>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    inner class SmsEntryViewHolder(
        private val binding: ChatEntryItemBinding,
        private val onItemClick: (ChatEntry) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: ChatEntry) = with(binding) {
            val context = binding.root.context

            sender.text = entry.address

            val message = entry.messages.first()

            preview.text = message.message
            sendTime.text = message.time

            root.setOnClickListener {
                onItemClick(entry)
            }
        }
    }
}