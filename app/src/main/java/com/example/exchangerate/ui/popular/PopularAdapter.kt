package com.example.exchangerate.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exchangerate.R
import com.example.exchangerate.databinding.PopularRateItemBinding
import com.example.exchangerate.model.ExchangeRate

class PopularAdapter(private val callback: (ExchangeRate) -> Unit) :
    ListAdapter<ExchangeRate, PopularAdapter.ViewHolder>(ExchangeDiffUtil()) {
    inner class ViewHolder(private val binding: PopularRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exchangeRate: ExchangeRate) {
            binding.currencyText.text = exchangeRate.currency
            binding.valueText.text = exchangeRate.rate.toString()

            Glide.with(binding.popularBtn).load(
                if (exchangeRate.selected) R.drawable.ic_selected_star else R.drawable.ic_unselected_star
            ).into(binding.popularBtn)
            binding.favoriteCard.setOnClickListener {
                callback(exchangeRate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PopularRateItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExchangeDiffUtil : DiffUtil.ItemCallback<ExchangeRate>() {
    override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem.currency == newItem.currency
    }

    override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem == newItem
    }
}