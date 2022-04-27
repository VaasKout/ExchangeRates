package com.example.exchangerate.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exchangerate.R
import com.example.exchangerate.data.DataState
import com.example.exchangerate.databinding.PopularFragmentBinding
import com.example.exchangerate.ui.common.MainFragmentViewModel
import com.example.exchangerate.utils.launchFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {
    private var _binding: PopularFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PopularViewModel>()

    private val mainFragmentViewModel by viewModels<MainFragmentViewModel>({ requireParentFragment().requireParentFragment() })

    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        popularAdapter = PopularAdapter {
            mainFragmentViewModel.scrollToTopEnabled = false
            viewModel.setItemSelected(it)
        }
        initUi()
        subscribeObservers()
    }

    private fun initUi() {
        binding.recyclerView.apply {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun subscribeObservers() {
        launchFlow {
            mainFragmentViewModel.remoteState.collect {
                when (it) {
                    DataState.Loading -> {}
                    is DataState.Error -> {
                        viewModel.getExchangeItems()
                    }
                    is DataState.Success<*> -> {
                        viewModel.getExchangeItems()
                    }
                    else -> {}
                }
            }
        }

        launchFlow {
            viewModel.exchangeList.collect {
                it?.let {
                    if (it.isEmpty()) {
                        binding.recyclerView.visibility = View.INVISIBLE
                        binding.badConnectCard.visibility = View.VISIBLE
                        Glide.with(binding.badConnectIcon).load(R.drawable.ic_bad_connect)
                            .into(binding.badConnectIcon)
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.badConnectCard.visibility = View.INVISIBLE
                        popularAdapter.submitList(it)
                        if (mainFragmentViewModel.scrollToTopEnabled) {
                            binding.recyclerView.itemAnimator = object : DefaultItemAnimator() {
                                override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
                                    super.onAnimationFinished(viewHolder)
                                    binding.recyclerView.layoutManager?.scrollToPosition(0)
                                }
                            }
                        } else {
                            binding.recyclerView.itemAnimator = null
                        }
                    }
                }
            }
        }
    }
}

