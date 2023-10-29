package net.example.smsreader.presenter.sms_list

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import net.example.smsreader.R
import net.example.smsreader.databinding.FragmentSmsListBinding

class SmsListFragment : Fragment(R.layout.fragment_sms_list) {

    private val binding: FragmentSmsListBinding by viewBinding()
    private val viewModel: SmsListViewModel by viewModels()

}