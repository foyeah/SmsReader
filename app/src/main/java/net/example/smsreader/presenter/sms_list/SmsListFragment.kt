package com.example.kotlin_sms.presenter.sms_list

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.example.smsreader.R
import net.example.smsreader.data.ChatEntry
import net.example.smsreader.data.SmsEntry
import net.example.smsreader.presenter.sms_list.SmsListClickListener
import net.example.smsreader.presenter.sms_list.SmsListViewModel
import net.example.smsreader.presenter.sms_list.SmsEntriesAdapter
import net.example.smsreader.databinding.FragmentSmsListBinding
import net.example.smsreader.requirePermission

class SmsListFragment : Fragment(R.layout.fragment_sms_list), SmsListClickListener {

    private val binding: FragmentSmsListBinding by viewBinding()
    private val viewModel: SmsListViewModel by viewModels()

    private val adapter = SmsEntriesAdapter(
        ::onSmsListItemClick,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chatMessageEntries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        initializeUI()
        initializeRecycler()
        requireSmsPermission()
    }

    private fun initializeUI() {
        initializeRecycler()
        showFailureMessage(false)
    }

    private fun requireSmsPermission() {
        requirePermission(
            permission = Manifest.permission.READ_SMS,
            successDelegate = {
                viewModel.loadSmsMessages(requireContext().contentResolver)
            },
            failureDelegate = {
                showFailureMessage(true)
            }
        )
    }

    private fun showFailureMessage(shown: Boolean) {
        val visibility = if(shown) View.VISIBLE else View.GONE
        binding.failSmsImageView.visibility = visibility
        binding.failSmsTitle.visibility = visibility
        binding.failSmsDescription.visibility = visibility
        binding.smsListRecycler.visibility = if (shown) View.GONE else View.VISIBLE
    }

    private fun fillMockData() {
        adapter.submitList(
            listOf(
                ChatEntry("Кто ты", listOf(SmsEntry("Hello world", "12:34", true))),
                ChatEntry("ААААААААААА", listOf(SmsEntry("Bye namespace", "19:12", true))),
                ChatEntry("OMG", listOf(SmsEntry("HIIIIIII!!!!!!", "11:34", true))),
            )
        )
    }

    //Old version
    private fun onChatItemClick(entry: ChatEntry) {
        Toast.makeText(requireContext(), entry.address, Toast.LENGTH_SHORT).show()
    }

    private fun initializeRecycler() = with(binding.smsListRecycler) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@SmsListFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onSmsListItemClick(entry: ChatEntry) {
        val direction = ChatFragmentDirections.actionSmsListFragmentToChatListFragment(smsListItem = entry)
        findNavController().navigate(direction)
    }
}