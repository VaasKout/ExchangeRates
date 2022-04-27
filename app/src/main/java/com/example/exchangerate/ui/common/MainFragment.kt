package com.example.exchangerate.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.exchangerate.R
import com.example.exchangerate.data.DataState
import com.example.exchangerate.databinding.MainFragmentBinding
import com.example.exchangerate.utils.launchFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        subscribeObservers()
    }

    private fun initUi() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (binding.swipeRefreshLayout.isRefreshing) {
                viewModel.loadRemoteExchangeItems()
            }
        }
        binding.currencyText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.currencyText.text.toString().isNotEmpty()) {
                    viewModel.setCurrency(binding.currencyText.text.toString())
                } else {
                    Snackbar.make(binding.root, "Text should not be empty", Snackbar.LENGTH_SHORT)
                        .show()
                }
                true
            } else {
                false
            }
        }

        binding.settingsIcon.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_main_fragment_screen_to_sort_fragment_screen)
        }

        Glide.with(binding.settingsIcon).load(R.drawable.ic_settings).into(binding.settingsIcon)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.popular_item -> {
                    if (!isValidDestination(R.id.popular_fragment_screen)) {
                        binding.bottomHostFragment.findNavController()
                            .navigate(R.id.popular_fragment_screen)
                    }
                    true
                }
                R.id.favorites_item -> {
                    if (!isValidDestination(R.id.favorites_fragment_screen)) {
                        binding.bottomHostFragment.findNavController()
                            .navigate(R.id.favorites_fragment_screen)
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun subscribeObservers() {
        launchFlow {
            viewModel.currencyValue.collect {
                viewModel.scrollToTopEnabled = true
                binding.currencyText.setText(it)
                viewModel.loadRemoteExchangeItems()
            }
        }
        launchFlow {
            viewModel.remoteState.collect {
                when (it) {
                    DataState.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                    is DataState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        showSnackBar(it.msg)
                    }
                    is DataState.Success<*> -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun isValidDestination(destination: Int): Boolean {
        return binding.bottomHostFragment.findNavController().currentDestination?.id == destination
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            "Something went wrong: $message",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}