package net.example.smsreader.presenter.chat_list

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.example.smsreader.R
import net.example.smsreader.data.ChatEntry
import net.example.smsreader.databinding.FragmentChatListBinding
import net.example.smsreader.requirePermission

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    private val binding: FragmentChatListBinding by viewBinding()
    private val args: ChatListFragmentArgs by navArgs()
    private val viewModel: ChatListViewModel by viewModels()

    private val adapter = ChatEntriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val smsList = args.smsList

        viewModel.messageEntries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initializeRecycler()
        requireSmsPermission(smsList)
    }

    private fun requireSmsPermission(chatEntry : ChatEntry) {
        requirePermission(
            permission = Manifest.permission.READ_SMS,
            successDelegate = {
                viewModel.loadSmsMessages(chatEntry)
            },
            failureDelegate = {

            }
        )
    }

    private fun initializeRecycler() = with(binding.messageListRecycler) {
        layoutManager = LinearLayoutManager(requireContext())
        (layoutManager as LinearLayoutManager).reverseLayout = true
        (layoutManager as LinearLayoutManager).stackFromEnd = false

        adapter = this@ChatListFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }
}