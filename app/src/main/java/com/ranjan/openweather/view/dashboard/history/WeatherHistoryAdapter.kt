package com.ranjan.openweather.view.dashboard.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ranjan.openweather.common.GeoLocation.Companion.getAddressFromLatAndLong
import com.ranjan.openweather.databinding.ItemLayoutBinding
import com.ranjan.openweather.data.database.entities.Weather

class WeatherHistoryAdapter :
    ListAdapter<Weather, WeatherHistoryAdapter.WeatherHistoryViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean =
                oldItem.time == newItem.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        return WeatherHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHistoryViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


    inner class WeatherHistoryViewHolder(private val binding: ItemLayoutBinding) :
        ViewHolder(binding.root) {
        fun bindData(item: Weather) {
            binding.weather = item
            binding.location.text = getAddressFromLatAndLong(
                binding.root.context, item.coordinate.latitude, item.coordinate.longitude
            )
        }
    }

}