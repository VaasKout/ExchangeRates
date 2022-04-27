package com.example.exchangerate.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerate.databinding.FavoriteRateItemBinding
import com.example.exchangerate.model.ExchangeRate

class FavoritesAdapter : ListAdapter<ExchangeRate, FavoritesAdapter.ViewHolder>(ExchangeDiffUtil()) {
    inner class ViewHolder(private val binding: FavoriteRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exchangeRate: ExchangeRate) {
            binding.currencyText.text = exchangeRate.currency
            binding.valueText.text = exchangeRate.rate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavoriteRateItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExchangeDiffUtil : DiffUtil.ItemCallback<ExchangeRate>() {
    override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem == newItem
    }
}