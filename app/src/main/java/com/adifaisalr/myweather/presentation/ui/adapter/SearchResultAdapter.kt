package com.adifaisalr.myweather.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.adifaisalr.myweather.R
import com.adifaisalr.myweather.databinding.ItemSearchBinding
import com.adifaisalr.myweather.domain.model.GeoLocationItem

/**
 * Adapter for the search result list.
 */
class SearchResultAdapter(
    private var users: ArrayList<GeoLocationItem> = arrayListOf(),
    private val actionFavoriteClickListener: ((GeoLocationItem, Int) -> Unit)? = null,
    private val actionSetDefaultClickListener: ((GeoLocationItem, Int) -> Unit)? = null
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.bind(item, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSearchBinding.inflate(inflater, parent, false))
    }

    inner class ViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GeoLocationItem, position: Int) {
            binding.searchItem = item
            binding.defaultBtn.isVisible = item.isDefault.not()
            val drawable =
                if (item.isFavorite) ContextCompat.getDrawable(binding.root.context, R.drawable.ic_baseline_favorite_24)
                else ContextCompat.getDrawable(binding.root.context, R.drawable.ic_baseline_favorite_border_24)
            binding.favorite.setImageDrawable(drawable)
            actionFavoriteClickListener?.let { listener ->
                binding.favorite.setOnClickListener {
                    listener.invoke(item, position)
                }
            }
            actionSetDefaultClickListener?.let { listener ->
                binding.defaultBtn.setOnClickListener {
                    listener.invoke(item, position)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateData(city: GeoLocationItem, position: Int) {
        users[position] = city
        notifyItemChanged(position)
    }
}
