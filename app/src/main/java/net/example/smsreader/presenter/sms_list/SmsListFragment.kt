package net.example.smsreader.presenter.sms_list

import android.Manifest.permission.READ_SMS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.example.smsreader.R
import net.example.smsreader.data.SmsChatEntry
import net.example.smsreader.databinding.FragmentSmsListBinding
import net.example.smsreader.presenter.SmsEntriesAdapter
import net.example.smsreader.requirePermission

class SmsListFragment : Fragment(R.layout.fragment_sms_list) {

    private val binding: FragmentSmsListBinding by viewBinding()
    private val viewModel: SmsListViewModel by viewModels()
    private val adapter = SmsEntriesAdapter(
        ::onChatItemClick,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecycler()
        requireSmsPermission()
    }

    private fun requireSmsPermission() {
        requirePermission(
            permission = READ_SMS,
            successDelegate = {
                fillMockData()
            },
            failureDelegate = {

            }
        )
    }



    private fun fillMockData() {
        adapter.submitList(
            listOf(
                SmsChatEntry("900", listOf("Hello world")),
                SmsChatEntry("8 800 555 35 35", listOf("Hello motherfucking world")),
                SmsChatEntry("DODO", listOf("Want pizza?")),
                SmsChatEntry("Son", listOf("MAD")),
            )
        )
    }

    private fun onChatItemClick(entry: SmsChatEntry) {
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

}