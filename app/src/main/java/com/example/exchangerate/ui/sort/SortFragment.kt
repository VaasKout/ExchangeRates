package com.example.exchangerate.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.exchangerate.R
import com.example.exchangerate.data.cache.SortedState
import com.example.exchangerate.databinding.SortFragmentBinding
import com.example.exchangerate.utils.launchFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment: Fragment() {
    private var _binding: SortFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SortFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SortFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        subscribeObservers()
    }

    private fun initUi() {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when(id) {
                R.id.alphabet_asc_button -> {
                    viewModel.setFlag(SortedState.ALPHABET_ACS)
                }
                R.id.alphabet_desc_button -> {
                    viewModel.setFlag(SortedState.ALPHABET_DESC)
                }
                R.id.rate_asc_order -> {
                    viewModel.setFlag(SortedState.VALUE_ACS)
                }
                R.id.rate_desc_order -> {
                    viewModel.setFlag(SortedState.VALUE_DESC)
                }
            }
        }

        binding.settingsToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack()
        }
    }

    private fun subscribeObservers() {
        launchFlow {
            viewModel.flag.collect {
                when(it) {
                    SortedState.ALPHABET_ACS -> {
                        binding.alphabetAscButton.isChecked = true
                    }
                    SortedState.ALPHABET_DESC -> {
                        binding.alphabetDescButton.isChecked = true
                    }
                    SortedState.VALUE_ACS -> {
                        binding.rateAscOrder.isChecked = true
                    }
                    SortedState.VALUE_DESC -> {
                        binding.rateDescOrder.isChecked = true
                    }
                    else -> {}
                }
            }
        }
    }
}