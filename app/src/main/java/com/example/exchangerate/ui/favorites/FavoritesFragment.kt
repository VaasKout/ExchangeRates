package com.example.exchangerate.ui.favorites

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
import com.example.exchangerate.databinding.FavoriteFragmentBinding
import com.example.exchangerate.ui.common.MainFragmentViewModel
import com.example.exchangerate.utils.launchFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoritesViewModel>()

    private lateinit var favoritesAdapter: FavoritesAdapter
    private val mainFragmentViewModel by viewModels<MainFragmentViewModel>({ requireParentFragment().requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        subscribeObservers()
    }

    private fun initUi() {
        favoritesAdapter = FavoritesAdapter()

        binding.favoriteRecyclerView.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun subscribeObservers() {
        launchFlow {
            mainFragmentViewModel.remoteState.collect {
                when (it) {
                    DataState.Loading -> {
                    }
                    is DataState.Error -> {
                        viewModel.getFavoriteItems()
                    }
                    is DataState.Success<*> -> {
                        viewModel.getFavoriteItems()
                    }
                    else -> {

                    }
                }
            }
        }

        launchFlow {
            viewModel.favoriteItems.collect {
                it?.let {
                    if (it.isNotEmpty()) {
                        favoritesAdapter.submitList(it)
                        binding.favoriteRecyclerView.visibility = View.VISIBLE
                        binding.warningCard.visibility = View.INVISIBLE
                        binding.favoriteRecyclerView.itemAnimator = object : DefaultItemAnimator() {
                            override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
                                super.onAnimationFinished(viewHolder)
                                binding.favoriteRecyclerView.layoutManager?.scrollToPosition(0)
                            }
                        }
                    } else {
                        binding.favoriteRecyclerView.visibility = View.INVISIBLE
                        binding.warningCard.visibility = View.VISIBLE
                        Glide.with(binding.starIcon).load(R.drawable.ic_selected_star)
                            .into(binding.starIcon)
                    }
                }
            }
        }
    }
}